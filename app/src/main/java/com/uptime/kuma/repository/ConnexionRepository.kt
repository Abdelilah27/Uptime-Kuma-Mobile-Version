package com.uptime.kuma.repository

import android.util.Log
import com.uptime.kuma.services.ConnexionService

class ConnexionRepository(private val connexionService: ConnexionService) {
    fun sendMessage(param: String) {
        Log.d("C", "sendQuery: ")
        val response = connexionService.sendMessage(param)
    }

}