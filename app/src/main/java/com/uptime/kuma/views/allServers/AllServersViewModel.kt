package com.uptime.kuma.views.allServers

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.uptime.kuma.models.serverCalcul.ServerCalcul
import com.uptime.kuma.views.mainActivity.MainActivity
import java.util.*

class AllServersViewModel : ViewModel() {
    val tempMonitors: ArrayList<ServerCalcul> = ArrayList() //for searching monitor

    init {
        tempMonitors.addAll(MainActivity.sharedViewModel.monitorCalcul)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun searchMonitor(searchText: String) {
        tempMonitors.clear()
        if (searchText.isNotEmpty()) {
            MainActivity.sharedViewModel.monitorCalcul.forEach {
                val monitor = MainActivity.sharedViewModel.getMonitorById(it.monitor_id)
                if (monitor.name.toLowerCase(Locale.getDefault()).contains(searchText)) {
                    tempMonitors.add(it)
                }
            }
            AllServersFragment.allRecycler.adapter!!.notifyDataSetChanged()
        } else {
            tempMonitors.clear()
            tempMonitors.addAll(MainActivity.sharedViewModel.monitorCalcul)
            AllServersFragment.allRecycler.adapter!!.notifyDataSetChanged()
        }
    }


}