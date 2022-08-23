package com.uptime.kuma.views.allServers

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.uptime.kuma.models.monitor.Monitor
import com.uptime.kuma.views.mainActivity.MainActivity
import java.util.*

class AllServersViewModel : ViewModel() {
    val tempMonitors: ArrayList<Monitor> = ArrayList() //for searching monitor

    init {
        tempMonitors.addAll(MainActivity.sharedViewModel.monitors)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun searchMonitor(searchText: String) {
        tempMonitors.clear()
        if (searchText.isNotEmpty()) {
            MainActivity.sharedViewModel.monitors.forEach {
                if (it.name.toLowerCase(Locale.getDefault()).contains(searchText)) {
                    tempMonitors.add(it)
                }
            }
            AllServersFragment.allRecycler.adapter!!.notifyDataSetChanged()
        } else {
            tempMonitors.clear()
            tempMonitors.addAll(MainActivity.sharedViewModel.monitors)
            AllServersFragment.allRecycler.adapter!!.notifyDataSetChanged()
        }
    }


}