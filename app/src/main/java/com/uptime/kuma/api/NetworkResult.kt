package com.uptime.kuma.api

class NetworkResult {
    // 0 -> Connexion opened
    // 1 -> Success response
    // 2 -> failure response
    // 3 -> error response

    private lateinit var response: String
    fun get(): String {
        return this.response
    }

    companion object {
        lateinit var instance: NetworkResult
    }

    init {
        instance = this
    }

    fun set(response: String) {
        this.response = response
    }
}