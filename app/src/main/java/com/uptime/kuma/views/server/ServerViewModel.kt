package com.uptime.kuma.views.server

import androidx.lifecycle.ViewModel
import com.uptime.kuma.models.monitor.Monitor
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem
import com.uptime.kuma.views.mainActivity.MainActivity

class ServerViewModel : ViewModel() {
    //get status of the server
    fun filterMonitorstatus(id: Int): ArrayList<MonitorStatusItem> {
        val filtredList: ArrayList<MonitorStatusItem> = ArrayList()
        filtredList.clear()
        MainActivity.sharedViewModel.monitorStatusList.forEach {
            if (it.monitorID == id) {
                filtredList.add(it)
            }
        }
        return filtredList
    }

    //get monitor by id
    fun getMonitorById(id: Int): Monitor {
        MainActivity.sharedViewModel.monitors.forEach {
            if (it.id == id) {
                return it
            }
        }
        return MainActivity.sharedViewModel.monitors[0]
    }

}