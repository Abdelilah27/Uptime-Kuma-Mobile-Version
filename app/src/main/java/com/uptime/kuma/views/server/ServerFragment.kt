package com.uptime.kuma.views.server

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentServerBinding
import com.uptime.kuma.views.adapters.ServerAdapter


class ServerFragment : Fragment(R.layout.fragment_server) {
    private val args: ServerFragmentArgs by navArgs()
    private lateinit var serverViewModel: ServerViewModel

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentServerBinding.bind(view)
        val serverAdapter = ServerAdapter(requireContext())
        serverViewModel = ViewModelProvider(requireActivity())[ServerViewModel::class.java]
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
        }

        //get monitor
        val monitor = serverViewModel.getMonitorById(serverId.toInt())
        binding.apply {
            serverTitle.text = monitor.name
            serverLink.text = monitor.url
            statusVerificationServerFragment.text =
                resources.getString(R.string.status_verification_server_fragment) + " " + monitor
                    .retryInterval.toString() + " " + resources.getString(
                    R.string.seconds
                )
        }


    }

}