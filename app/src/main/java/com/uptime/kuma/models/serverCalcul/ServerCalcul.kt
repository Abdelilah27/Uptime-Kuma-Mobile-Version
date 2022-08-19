package com.uptime.kuma.models.serverCalcul

data class ServerCalcul(
    val duration: Int?= null,
    val id: Int?= null,
    val important: Int?= null,
    val monitor_id: Int?= null,
    val msg: String?= null,
    val ping: Int?= null,
    val status: Int?= null,
    val time: String ?= null
)