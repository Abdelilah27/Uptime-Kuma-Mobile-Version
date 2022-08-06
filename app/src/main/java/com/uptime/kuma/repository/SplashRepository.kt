package com.uptime.kuma.repository

import com.tinder.scarlet.WebSocket
import com.uptime.kuma.api.ConnexionInterface
import io.reactivex.Flowable

class SplashRepository(private val connexionInterface: ConnexionInterface) {

    fun getData(): Flowable<WebSocket.Event> {
        return connexionInterface.observeConnection()
    }
}