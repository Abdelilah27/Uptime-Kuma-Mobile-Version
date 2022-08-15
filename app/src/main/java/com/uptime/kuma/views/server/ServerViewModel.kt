package com.uptime.kuma.views.server

import androidx.lifecycle.ViewModel
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem
import com.uptime.kuma.views.dashbord.DashbordCompanionObject

class ServerViewModel : ViewModel() {
    //get status of the server
    fun filterMonitorstatus(id: Int): ArrayList<MonitorStatusItem> {
        val filtredList: ArrayList<MonitorStatusItem> = ArrayList()
        filtredList.clear()
        DashbordCompanionObject.monitorStatusList.forEach {
            if (it.monitorID == id) {
                filtredList.add(it)
            }
        }
        return filtredList
    }
}