package com.uptime.kuma.service.sharedData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable
import kotlinx.coroutines.launch

class SharedViewModel(private val sharedRepository: SharedRepository) : ViewModel() {

    fun sendQuery(param: String) {
        viewModelScope.launch {
            sharedRepository.sendMessage(param)
        }
    }

    //Get Data
    val data: Flowable<WebSocket.Event>
        get() = sharedRepository.getData()
}