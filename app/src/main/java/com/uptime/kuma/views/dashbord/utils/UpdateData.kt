package com.uptime.kuma.views.dashbord.utils

import com.uptime.kuma.models.monitorStatus.MonitorStatusItem

interface UpdateData {

    fun onReceivedData(data:MonitorStatusItem)
}