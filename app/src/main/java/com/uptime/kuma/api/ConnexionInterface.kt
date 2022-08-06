package com.uptime.kuma.api

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import com.uptime.kuma.models.monitor.Monitor
import io.reactivex.Flowable

interface ConnexionInterface {
    @Receive
    fun observeConnection(): Flowable<WebSocket.Event>

    @Send
    fun sendMessage(param: String)

//    @Receive
//    fun observeMonitorsList(): Flowable<Monitor>

}