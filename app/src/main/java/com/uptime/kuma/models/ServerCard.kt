package com.uptime.kuma.models

import com.uptime.kuma.models.monitor.Monitor
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem

class ServerCard(val monitor: Monitor, val listOfStatus: List<MonitorStatusItem>) {
}