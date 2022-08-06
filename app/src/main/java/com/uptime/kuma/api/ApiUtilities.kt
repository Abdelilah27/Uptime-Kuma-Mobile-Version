package com.uptime.kuma.api

import android.app.Application
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.uptime.kuma.utils.Constants
import okhttp3.OkHttpClient

object ApiUtilities {
    fun provideScarlet(application: Application): Scarlet {
        val lifecycle = AndroidLifecycle.ofApplicationForeground(application)
        val okHttpClient = OkHttpClient.Builder().build()
        val streamAdapterFactory = RxJava2StreamAdapterFactory()
        return Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory(Constants.socketUrl))
            .lifecycle(lifecycle)
            .addStreamAdapterFactory(streamAdapterFactory)
            .build()
    }

    fun getInstance(scarlet: Scarlet): ConnexionInterface {
        return scarlet.create()
    }

}