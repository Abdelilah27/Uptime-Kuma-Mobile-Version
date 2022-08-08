package com.uptime.kuma.utils

object Constants {
    const val socketUrl = "ws://status.mobiblanc.tech/socket.io/?EIO=4&transport=websocket"
    const val socketUrl1 = "wss://ws.bitmex.com/realtime?subscribe=instrument,orderBookL2_25:XBTUSD"
    const val dataQuery = "40"
    const val unSuccessConnexion = "Failed to connect"
    const val successConnexion = "\"upgrades\":[],\"pingInterval\":25000,\"pingTimeout\":20000"
    const val closedConnexion = "shutdownReason"
    const val monitorListSuffix = "42[\"monitorList\","
}