package com.uptime.kuma.models.monitorStatus

data class MonitorStatusItem(
    val duration: Int? = null,
    val important: Boolean? = null,
    val monitorID: Int? = null,
    val msg: String? = null,
    val ping: String? = null,
    val status: Int? = null,
    val time: String? = null,
    val name: String? = null
)