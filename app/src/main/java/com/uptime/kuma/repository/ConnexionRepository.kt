package com.uptime.kuma.repository

import android.util.Log
import com.uptime.kuma.services.ConnexionService

class ConnexionRepository(private val connexionService: ConnexionService) {
    fun sendMessage(param: String) {
        val response = connexionService.sendMessage(param)
    }

}