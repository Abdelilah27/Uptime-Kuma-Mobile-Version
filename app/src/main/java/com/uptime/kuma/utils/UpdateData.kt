package com.uptime.kuma.utils

import com.uptime.kuma.models.monitorStatus.MonitorStatusItem

interface UpdateData {
    fun onReceivedData(data:MonitorStatusItem)

}