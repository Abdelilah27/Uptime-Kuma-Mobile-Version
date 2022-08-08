package com.uptime.kuma.service.sharedData

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.tinder.scarlet.WebSocket
import com.uptime.kuma.api.NetworkResult
import com.uptime.kuma.utils.Constants
import io.reactivex.Flowable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class SharedViewModel(private val sharedRepository: SharedRepository) : ViewModel() {

//    private val _monitorLiveData = MutableLiveData<List<Monitor>>()
//    val monitorLiveData: LiveData<List<Monitor>>
//        get() = _monitorLiveData

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
            getMonitorsFromResponse(
                response,
                Constants.monitorListSuffix,
            )
        }, { error ->
            NetworkResult.instance.get().postValue("3")//set error
            Log.d("error: ", error.toString())
        })
    }

    //get Monitors List
    private fun getMonitorsFromResponse(response: WebSocket.Event?, suffix: String) {
        if (response.toString().contains(suffix)) {
            // deleting suffix part
            val customResponseAfter = response.toString().substringAfter(
                Constants.monitorListSuffix
            )
            //parse response to JSON
            //delete ] at the end of the response
            val customResponseEnd = customResponseAfter.dropLast(0)
            //transform to jsonObject
            val jsonObject = JSONObject(customResponseEnd)
            for (i in 1 until jsonObject.length()) {
                if (jsonObject.has(i.toString())) {
                    Log.d("REA", i.toString())
                    Log.d("jsonObject1", jsonObject.getJSONObject(i.toString()).toString())
                } else {
                    continue
                }
            }
        }
    }
}

