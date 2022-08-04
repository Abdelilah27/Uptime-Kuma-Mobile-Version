package com.uptime.kuma.views.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinder.scarlet.WebSocket
import com.uptime.kuma.repository.SplashRepository
import io.reactivex.Flowable
import kotlinx.coroutines.launch


class SplashViewModel(private val splashRepository: SplashRepository) : ViewModel() {

    fun sendQuery(param: String) {
        viewModelScope.launch {
            splashRepository.sendMessage(param)
        }
    }

    //Get Data
    val data: Flowable<WebSocket.Event>
        get() = splashRepository.getData()
}
