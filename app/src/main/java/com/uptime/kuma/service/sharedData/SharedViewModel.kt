package com.uptime.kuma.service.sharedData

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.tinder.scarlet.WebSocket
import com.uptime.kuma.api.NetworkResult
import com.uptime.kuma.models.monitor.Monitor
import com.uptime.kuma.utils.Constants
import io.reactivex.Flowable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedViewModel(private val sharedRepository: SharedRepository) : ViewModel() {

    private val _monitorLiveData = MutableLiveData<List<Monitor>>()
    val monitorLiveData: LiveData<List<Monitor>>
        get() = _monitorLiveData

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
    fun handleConnexionState(lifecycleOwner: LifecycleOwner, lifecycleScope: CoroutineScope) {
        NetworkResult().set(MutableLiveData("0"))//set connexion to open
        data.subscribe({ response ->
            lifecycleScope.launch {
                NetworkResult.instance.get().observe(lifecycleOwner, Observer {
                    if (response.toString()
                            .contains(Constants.successConnexion) && NetworkResult
                            .instance.get().value == "0"
                    ) {
                        sendQuery(Constants.dataQuery)
                        NetworkResult.instance.get().postValue("1") //Success response
                    }
                })

            }
            Log.d("TAG", response.toString())
        }, { error ->
            NetworkResult.instance.get().postValue("3")//set error
            Log.d("error: ", error.toString())
        })
    }

//    //get Monitors item
//    private fun getMonitorsItem(response: WebSocket.Event?, suffix: String, prefix: String) {
//
//    }

    //extract String between two string
    private fun extractResponse(response: String, suffix: String, prefix: String) {
        val after =
            response.substringAfter(suffix)
        val before =
            after.substringBefore(prefix)
        Log.d("before4", before)
    }


}

