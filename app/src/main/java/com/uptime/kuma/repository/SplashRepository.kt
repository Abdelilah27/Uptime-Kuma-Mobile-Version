package com.uptime.kuma.repository

import com.uptime.kuma.services.ConnexionInterface

class SplashRepository(private val connexionInterface: ConnexionInterface) {
    fun sendMessage(param: String) {
        val response = connexionInterface.sendMessage(param)
    }

}