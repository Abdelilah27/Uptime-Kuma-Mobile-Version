package com.uptime.kuma.service.sharedData

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.tinder.scarlet.WebSocket
import com.uptime.kuma.api.NetworkResult
import com.uptime.kuma.models.monitor.Monitor
import com.uptime.kuma.repository.SharedRepository
import com.uptime.kuma.utils.Constants
import io.reactivex.Flowable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class SharedViewModel(private val sharedRepository: SharedRepository) : ViewModel() {

    private val monitors: ArrayList<Monitor> = ArrayList()
    private val _monitorLiveData = MutableLiveData<ArrayList<Monitor>>()
    val monitorLiveData: LiveData<ArrayList<Monitor>>
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
            getMonitorsFromResponse(
                response,
                Constants.monitorListSuffix,
            )
            Log.d("TAG", response.toString())
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
                    //get separated jsonObjects
                    val json = jsonObject.getJSONObject(i.toString())
                    val name = json.get("name").toString()
                    val id = json.get("id")
                    val active = json.get("active")
                    val dns_resolve_server = json.get("dns_resolve_server").toString()
                    val dns_resolve_type = json.get("dns_resolve_type").toString()
                    val expiryNotification = json.get("expiryNotification")
                    val ignoreTls = json.get("ignoreTls")
                    val interval = json.get("interval")
                    val maxredirects = json.get("maxredirects")
                    val maxretries = json.get("maxretries")
                    val method = json.get("method").toString()
                    val type = json.get("type").toString()
                    val upsideDown = json.get("upsideDown")
                    val url = json.get("url").toString()
                    val weight = json.get("weight")
                    val retryInterval = json.get("retryInterval")
                    //accepted_statuscodes list
                    var accepted_statuscodes: ArrayList<String> = ArrayList()
                    val accepted_statuscodes_array =
                        json.getJSONArray("accepted_statuscodes")
                    for (j in 0 until accepted_statuscodes_array.length()) {
                        val statusCode = accepted_statuscodes.add(
                            accepted_statuscodes_array[j]
                                .toString()
                        )
                        accepted_statuscodes.add(statusCode.toString())
                    }
                    //init monitor
                    val monitor = Monitor(
                        id = id as Int,
                        name = name,
                        active =
                        active as Int,
                        dns_resolve_server = dns_resolve_server,
                        dns_resolve_type =
                        dns_resolve_type,
                        expiryNotification = expiryNotification as Boolean,
                        ignoreTls = ignoreTls as Boolean,
                        interval = interval as Int,
                        maxredirects = maxredirects as Int,
                        maxretries = maxretries as Int,
                        method = method,
                        type = type,
                        upsideDown = upsideDown as Boolean,
                        url = url,
                        weight = weight as Int,
                        retryInterval = retryInterval as Int,
                        accepted_statuscodes = accepted_statuscodes
                    )
                    //add monitors to ArrayList
                    monitors.add(monitor)

                } else {
                    continue
                }
            }
            //add ArrayList to MutableLiveData
            _monitorLiveData.postValue(monitors)
        }
    }
}

