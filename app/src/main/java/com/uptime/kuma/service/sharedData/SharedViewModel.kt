package com.uptime.kuma.service.sharedData

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinder.scarlet.WebSocket
import com.uptime.kuma.api.NetworkResult
import com.uptime.kuma.utils.Constants
import io.reactivex.Flowable
import kotlinx.coroutines.launch

class SharedViewModel(private val sharedRepository: SharedRepository) : ViewModel() {
    private var connexion: Boolean = false

    //Get Data
    val data: Flowable<WebSocket.Event>
        get() = sharedRepository.getData()

    private fun sendQuery(param: String) {
        viewModelScope.launch {
            sharedRepository.sendMessage(param)
        }
    }


    //Send query after opening the connexion
    @SuppressLint("CheckResult")
    fun listeningToResponse() {
        NetworkResult().set("0") //set connexion to open
        data.subscribe({ response ->
            if (response.toString().contains(Constants.successConnexion) || NetworkResult
                    .instance.get() != "1"
            ) {
                Log.d("EFG", "listeningToResponse: ")
                sendQuery(Constants.dataQuery)
                NetworkResult().set("1")
            }
            Log.d("response: ", response.toString())
        }, { error ->
            Log.d("error: ", error.toString())
        })
    }


    //get Monitors item


}