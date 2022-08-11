package com.uptime.kuma.models.monitorStatus

data class MonitorStatusItem(
    val duration: Int ?= null,
    val important: Int ?= null,
    val monitorID: Int ?= null,
    val msg: String ?= null,
    val ping: Int ?= null,
    val status: Int ?= null,
    val time: String?= null
)