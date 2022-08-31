package com.uptime.kuma.utils

object Constants {
    const val socketUrl = "ws://status.mobiblanc.tech/socket.io/?EIO=4&transport=websocket"
    const val openedConnexion = "OnConnectionOpened"
    const val dataQuery = "40"
    const val dataQueryResend = "3"
    const val unSuccessConnexion = "ailed to connect to"
    const val successConnexion = "value=0"
    const val emission = "value=2"
    const val closedConnexion = "shutdownReason=ShutdownReason"
    const val monitorListSuffix = "42[\"monitorList\","
    const val statusListSuffix = "42[\"statusPageList\",{"
    const val dashbordMonitorItemsSuffix = "42[\"importantHeartbeatList\","
    const val dashbordMonitorUpdate = "42[\"heartbeat\","
    const val heartbeatlist = "42[\"heartbeatList\","
    const val autoLogin = "autoLogin"
    const val login = "autoLogin"
    const val successLogin = "\"ok\":true"
    const val unSuccessLogin = "value=430[{\"ok\":false"
}