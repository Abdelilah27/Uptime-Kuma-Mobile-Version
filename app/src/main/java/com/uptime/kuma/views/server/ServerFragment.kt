package com.uptime.kuma.views.server

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uptime.kuma.R
import com.uptime.kuma.api.ConnexionLifecycle
import com.uptime.kuma.databinding.FragmentServerBinding
import com.uptime.kuma.models.monitor.Monitor
import com.uptime.kuma.views.adapters.MonitorItemAllServersCardAdapter
import com.uptime.kuma.views.adapters.ServerAdapter
import com.uptime.kuma.views.mainActivity.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ServerFragment : Fragment(R.layout.fragment_server) {
    private val args: ServerFragmentArgs by navArgs()
    private lateinit var serverViewModel: ServerViewModel

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentServerBinding.bind(view)
        val serverAdapter = ServerAdapter(requireContext())
        val serverCardAdapter = MonitorItemAllServersCardAdapter(requireContext())
        serverViewModel = ViewModelProvider(requireActivity())[ServerViewModel::class.java]
        var monitor: Monitor
        //get Id from args
        val serverId = args.serverId
        binding.apply {
            recyclerServerFragment.apply {
                adapter = serverAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                isNestedScrollingEnabled = false
                serverAdapter.submitList(serverViewModel.filterMonitorstatus(serverId.toInt()))
            }
            cardGraphRecyclerServer.apply {
                adapter = serverCardAdapter
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

                MainActivity.sharedViewModel.monitorCalculLiveData.observe(
                    viewLifecycleOwner,
                    Observer {
                        val status =
                            MainActivity.sharedViewModel.getStatuesServerById(serverId.toInt())
                        serverCardAdapter.setData(
                            status
                        )
                        when (status[0].status) {
                            0 -> {
                                isOnlineText.resources.getString(R.string.offline)
                                isOnlineCard.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.background_no_active_item_all_server_fragment
                                    )
                                )
                            }
                            1 -> {
                                isOnlineText.resources.getString(R.string.online)
                                isOnlineCard.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color
                                            .background_active_item_all_server_fragment
                                    )
                                )
                            }
                            else -> {
                                isOnlineText.resources.getString(R.string.attente)
                                isOnlineCard.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color
                                            .attente
                                    )
                                )

                            }
                        }
                    })

            }
        }

        //get monitor
        CoroutineScope(Dispatchers.IO).launch {
            monitor = serverViewModel.getMonitorById(serverId.toInt())
            binding.serverLink.text = monitor.url
            binding.serverTitle.text = monitor.name
            binding.statusVerificationServerFragment.text =
                resources.getString(R.string.status_verification_server_fragment) + " " + monitor
                    .retryInterval.toString() + " " + resources.getString(
                    R.string.seconds
                )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ConnexionLifecycle.closeConnexion()
    }

}