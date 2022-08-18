package com.uptime.kuma.models.monitorUpdate

data class MonitorUpdate(
    val duration: Int?= null,
    val important: Boolean  ?= null,
    val monitorID: Int ?= null,
    val msg: String ?= null,
    val ping: Int ?= null,
    val status: Int ?= null,
    val time: String?= null,
    val name: String?= null
)