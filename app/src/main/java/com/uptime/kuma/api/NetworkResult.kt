package com.uptime.kuma.api

import androidx.lifecycle.MutableLiveData

class NetworkResult {
    // 0 -> Connexion opened
    // 1 -> Success response
    // 2 -> failure response
    // 3 -> error response

    var response: MutableLiveData<String> = MutableLiveData()

    fun get(): MutableLiveData<String> {
        return this.response
    }

    companion object {
        lateinit var instance: NetworkResult
    }

    init {
        instance = this
    }

    fun set(response: MutableLiveData<String>) {
        this.response = response
    }
}