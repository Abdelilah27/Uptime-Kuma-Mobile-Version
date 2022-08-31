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
import com.github.mikephil.charting.data.Entry
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentServerBinding
import com.uptime.kuma.models.monitor.Monitor
import com.uptime.kuma.models.serverCalcul.ServerCalcul_Items
import com.uptime.kuma.views.adapters.MonitorItemAllServersCardAdapter
import com.uptime.kuma.views.adapters.ServerAdapter
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
    var lineValues = ArrayList<Entry>()
    var executeOnce = true
    private var series1Number = arrayOf<Number>()
    var domainLabels = arrayOf<Number>()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentServerBinding.bind(view)
        val serverAdapter = ServerAdapter(requireContext())
        val serverCardAdapter = MonitorItemAllServersCardAdapter(requireContext())
        serverViewModel = ViewModelProvider(requireActivity())[ServerViewModel::class.java]
        var monitor: Monitor
        binding.serverback.setOnClickListener {
            MainActivity.navController.navigateUp()
        }

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
                        status =
                            MainActivity.sharedViewModel.getStatuesServerById(serverId.toInt())
                        serverCardAdapter.setData(
                            status
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

                        if (executeOnce) {
                            series1Number = setData()
                            print("Heyyyyyyy" + series1Number)
                            val series1: XYSeries = SimpleXYSeries(
                                Arrays.asList(*series1Number),
                                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                                "Temps de réponse en (ms)"
                            );
                            val series1Format = LineAndPointFormatter(
                                resources.getColor(R.color.main_color),
                                resources.getColor(R.color.attente),
                                null,
                                null
                            )
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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MainActivity.sharedViewModel.monitorCalculLiveData.removeObservers(viewLifecycleOwner)
        executeOnce = false
    }

//    private fun setData() { //for the chart
//        CoroutineScope(Dispatchers.IO).launch {
//            executeOnce = false
//            var timeFormat: String
//            var timeFormatReplaced: Float
//            status.sortBy { it.time }
//            Log.d("TAG", status.takeLast(16).forEach { it.time }.toString())
//            status.takeLast(16).forEach {
//                if (it.ping != null && it.ping != "null" && it.ping.isNotEmpty()) {
//                    timeFormat = it.time.toString().subSequence(11, 16).toString()
//                    timeFormatReplaced = timeFormat.replace(":", ".").toFloat()
//                    lineValues.add(Entry(timeFormatReplaced, it.ping.toFloat()))
//                }
//            }
//        }
//    }

    private fun setData(): Array<Number> { //for the chart
        executeOnce = false
        status.sortBy { it.time }
        var timeFormat: String
        var timeFormatReplaced: Float
        status.takeLast(10).forEach {
            if (it.ping != null && it.ping != "null" && it.ping.isNotEmpty()) {
                timeFormat = it.time.toString().subSequence(11, 16).toString()
                timeFormatReplaced = timeFormat.replace(":", ".").toFloat()
                var time = 1.2
                try {
                    time = String.format("%.2f", timeFormatReplaced).toDouble()
                } catch (e: Exception) {
                    Log.d("TAG", e.toString())
                }
//                    series1Number=addElement(series1Number,timeFormatReplaced)
                domainLabels = addElement(domainLabels, time)
                series1Number = addElement(series1Number, it.ping.toInt())
            }
        }
        return series1Number
    }


    fun addElement(arr: Array<Number>, element: Number): Array<Number> {
        val mutableArray = arr.toMutableList()
        mutableArray.add(element)
        return mutableArray.toTypedArray()
    }


//    private fun setLineChartData() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val lineDataSet = LineDataSet(lineValues, "Temps de réponse (ms)")
//            //We add features to our chart
//            lineDataSet.color = resources.getColor(R.color.attente)
//            binding.getTheGraph.description.isEnabled=false
//            binding.getTheGraph.legend.textColor=resources.getColor(R.color.attente)
//            lineDataSet.circleRadius = 5f
//            lineDataSet.setDrawFilled(true)
//            lineDataSet.valueTextSize = 50F
//            lineDataSet.fillColor = resources.getColor(R.color.main_color)
//            lineDataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER;
//            lineDataSet.setDrawValues(false);
//            val yAxisRight: YAxis = binding.getTheGraph.axisRight
//            val yAxisLeft: YAxis = binding.getTheGraph.axisLeft
//            val xAxis: XAxis = binding.getTheGraph.xAxis
//            xAxis.textColor = resources.getColor(R.color.main_color)
//            yAxisRight.textColor = resources.getColor(R.color.main_color)
//            yAxisLeft.textColor = resources.getColor(R.color.main_color)
//
//
//            //            lineDataSet.setLineWidth(1.75f);
//            //            lineDataSet.setCircleRadius(5f);
//            //            lineDataSet.setColor(Color.WHITE);
//                        lineDataSet.setCircleColor(resources.getColor(R.color.attente));
////                        lineDataSet.setHighLightColor(Color.WHITE);
//
//            //We connect our data to the UI Screen
//            val data = LineData(lineDataSet)
//            binding.getTheGraph.data = data
//            binding.getTheGraph.setBackgroundColor(resources.getColor(R.color.grey))
//            withContext(Dispatchers.Main) {
//                binding.getTheGraph.animateXY(2000, 2000, Easing.EaseInCubic)
//            }
//        }
//    }

}
