package com.uptime.kuma.api

import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.OkHttpClient

object ApiUtilities {
    var myLifecycle = CustomSocketLifecycle()
    fun provideScarlet(url: String): Scarlet {
        val okHttpClient = OkHttpClient.Builder().build()
        val streamAdapterFactory = RxJava2StreamAdapterFactory()
        return Scarlet.Builder()
            .lifecycle(myLifecycle)
            .webSocketFactory(okHttpClient.newWebSocketFactory(url))
            .addStreamAdapterFactory(streamAdapterFactory)
            .build()
    }

    fun getInstance(scarlet: Scarlet): ConnexionInterface {
        return scarlet.create()
    }

    fun closeConnexionA(scarlet: Scarlet) {

    }

}