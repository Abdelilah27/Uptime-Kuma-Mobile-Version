package com.uptime.kuma.models.serverCalcul



data class ServerCalcul (
    val monitor_id: Int,
    val monitorStatus:ArrayList<ServerCalcul_Items>
    )