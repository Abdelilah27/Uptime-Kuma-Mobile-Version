package com.uptime.kuma.repository

import com.tinder.scarlet.WebSocket
import com.uptime.kuma.services.ConnexionInterface
import io.reactivex.Flowable

class SplashRepository(private val connexionInterface: ConnexionInterface) {
    fun sendMessage(param: String) {
        val response = connexionInterface.sendMessage(param)
    }

    fun getData(): Flowable<WebSocket.Event> {
        return connexionInterface.observeConnection()
    }


}