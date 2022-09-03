package com.uptime.kuma.views.server

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYGraphWidget
import com.androidplot.xy.XYSeries
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentServerBinding
import com.uptime.kuma.models.monitor.Monitor
import com.uptime.kuma.models.serverCalcul.ServerCalcul_Items
import com.uptime.kuma.views.adapters.MonitorItemAllServersCardAdapter
import com.uptime.kuma.views.adapters.ServerAdapter
import com.uptime.kuma.views.allServers.AllServersFragment
import com.uptime.kuma.views.dashbord.DashboardFragment
import com.uptime.kuma.views.mainActivity.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.util.*
import kotlin.math.roundToInt


class ServerFragment : Fragment(R.layout.fragment_server) {
    private val args: ServerFragmentArgs by navArgs()
    private lateinit var serverViewModel: ServerViewModel
    private lateinit var binding: FragmentServerBinding
    private var status: ArrayList<ServerCalcul_Items> = ArrayList()
    private var series1Number = arrayOf<Number>()
    var domainLabels = arrayOf<Number>()
    private val TAG0 = "DashboardFragment"
    private val TAG1 = "AllServersFragment"
    private var excuteonce: Boolean = true
    private var serverId = "0"

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentServerBinding.bind(view)
        val serverAdapter = ServerAdapter(requireContext())
        val serverCardAdapter = MonitorItemAllServersCardAdapter(requireContext())
        serverViewModel = ViewModelProvider(requireActivity())[ServerViewModel::class.java]
        var monitor: Monitor
        //back button
        binding.serverback.setOnClickListener {
            MainActivity.navController.navigateUp()
        }

        //get Id from args
        val arg = args.serverId
        //to check if safe args are send from dashboard or allServers Fragment
        if (arg.contains(TAG0)) {
            serverId = arg.removeSuffix(TAG0)
        } else if (arg.contains(TAG1)) {
            serverId = arg.removeSuffix(TAG1)
        }
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
                        status =
                            MainActivity.sharedViewModel.getStatuesServerById(serverId.toInt())
                        serverCardAdapter.setData(
                            status.take(32)
                        )
                        when (status[0].status) {
                            0 -> {
                                isOnlineText.text = resources.getString(R.string.offline)
                                isOnlineCard.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.background_no_active_item_all_server_fragment
                                    )
                                )
                            }
                            1 -> {
                                isOnlineText.text = resources.getString(R.string.online)
                                isOnlineCard.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color
                                            .background_active_item_all_server_fragment
                                    )
                                )
                            }
                            else -> {
                                isOnlineText.text = resources.getString(R.string.attente)
                                isOnlineCard.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color
                                            .attente
                                    )
                                )

                            }
                        }

                        if (excuteonce) {
                            series1Number = setData()
                            val series1: XYSeries = SimpleXYSeries(
                                Arrays.asList(*series1Number),
                                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                                resources.getString(R.string.graphtitle)
                            );
                            val series1Format = LineAndPointFormatter(
                                resources.getColor(R.color.main_color),
                                resources.getColor(R.color.attente),
                                null,
                                null
                            )

                            series1Format.linePaint.strokeWidth = 10f
                            series1Format.vertexPaint.strokeWidth = 25f
                            binding.graphServerFragment.addSeries(series1, series1Format)
                            binding.graphServerFragment.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format =
                                object : Format() {
                                    override fun format(
                                        obj: Any?,
                                        toAppendTo: StringBuffer,
                                        pos: FieldPosition
                                    ): StringBuffer {
                                        val i = (obj as Number).toFloat().roundToInt()
                                        return toAppendTo.append(domainLabels[i])
                                    }

                                    override fun parseObject(
                                        source: String?,
                                        pos: ParsePosition
                                    ): Any? {
                                        return null
                                    }

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
            //dismiss dialog progress bar
            if (arg.contains(TAG0)) {
                DashboardFragment.progressDialog.dismiss()
            } else if (arg.contains(TAG1)) {
                AllServersFragment.progressDialogAllServers.dismiss()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MainActivity.sharedViewModel.monitorCalculLiveData.removeObservers(viewLifecycleOwner)

    }

    private fun setData(): Array<Number> { //for the chart
        excuteonce = false
        var time = 0.0
        status.sortBy { it.time }
        var timeFormat: String
        var timeFormatReplaced: Float
        status.takeLast(32).forEach {
            if (it.ping != null && it.ping != "null" && it.ping.isNotEmpty()) {
                timeFormat = it.time.toString().subSequence(11, 16).toString()
                timeFormatReplaced = timeFormat.replace(":", ".").toFloat()
                try {
                    //get 2 numbers after the point
                    val number3digits: Double = Math.round(timeFormatReplaced * 1000.0) / 1000.0
                    val number2digits: Double = Math.round(number3digits * 100.0) / 100.0
                    time = Math.round(number2digits * 100.0) / 100.0
                } catch (e: Exception) {
                    Log.d("TAG", e.toString())
                }
                domainLabels = addElement(domainLabels, time)
                series1Number = addElement(series1Number, it.ping.toInt())
            }
        }
        return series1Number
    }


    private fun addElement(arr: Array<Number>, element: Number): Array<Number> {
        val mutableArray = arr.toMutableList()
        mutableArray.add(element)
        return mutableArray.toTypedArray()
    }

}
