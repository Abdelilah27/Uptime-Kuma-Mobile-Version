package com.uptime.kuma.repository

import com.tinder.scarlet.WebSocket
import com.uptime.kuma.api.ConnexionInterface
import io.reactivex.Flowable

class SharedRepository(private val connexionInterface: ConnexionInterface) {
    fun sendMessage(param: String) {
        val response = connexionInterface.sendMessage(param)
    }

   fun getData(): Flowable<WebSocket.Event> {
        return connexionInterface.observeConnection()
    }
}