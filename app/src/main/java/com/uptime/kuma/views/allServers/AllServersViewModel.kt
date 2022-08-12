package com.uptime.kuma.views.allServers

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.uptime.kuma.models.monitor.Monitor
import com.uptime.kuma.views.monitorsList.AllServersCompanionObject
import java.util.*

class AllServersViewModel : ViewModel() {

    val tempMonitors: ArrayList<Monitor> = ArrayList() //for searching monitor
    val monitors = AllServersCompanionObject.monitors //get monitors list


    init {
        tempMonitors.addAll(monitors)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun searchMonitor(searchText: String) {
        tempMonitors.clear()
        if (searchText.isNotEmpty()) {
            monitors.forEach {
                if (it.name.toLowerCase(Locale.getDefault()).contains(searchText)) {
                    tempMonitors.add(it)
                }
            }
            AllServersFragment.allRecycler.adapter!!.notifyDataSetChanged()
        } else {
            tempMonitors.clear()
            tempMonitors.addAll(monitors)
            AllServersFragment.allRecycler.adapter!!.notifyDataSetChanged()
        }
    }

}