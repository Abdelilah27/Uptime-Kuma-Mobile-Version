package com.uptime.kuma.service.sharedData

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.*
import com.tinder.scarlet.WebSocket
import com.uptime.kuma.api.NetworkResult
import com.uptime.kuma.repository.SharedRepository
import com.uptime.kuma.utils.Constants
import com.uptime.kuma.views.dashbord.DashbordCompanionObject
import com.uptime.kuma.views.monitorsList.AllServersCompanionObject
import com.uptime.kuma.views.status.StatusCompanionObject
import io.reactivex.Flowable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedViewModel(private val sharedRepository: SharedRepository) : ViewModel() {

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
                    //to show error dialog after a delay
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (NetworkResult.instance.get().value == "0") {
                            NetworkResult.instance.get().postValue("6") //no response
                        }
                    }, 30000)
                    if (response.toString()
                            .contains(Constants.successConnexion) && NetworkResult
                            .instance.get().value == "0"
                    ) {
                        sendQuery(Constants.dataQuery)
                        NetworkResult.instance.get().postValue("1") //Success response
                    } else if (response.toString().contains(Constants.emission)) {
                        sendQuery(Constants.dataQueryResend)
                        NetworkResult.instance.get().postValue("5") //Resend response
                    } else if (response.toString()
                            .contains(Constants.unSuccessConnexion) && NetworkResult
                            .instance.get().value == "0"
                    ) {
                        NetworkResult.instance.get().postValue("2") //Failed connexion
                    }
                })
            }
            AllServersCompanionObject.getMonitorsFromResponse(
                response,
                Constants.monitorListSuffix,
            )

            DashbordCompanionObject.getDashbordMonitorItem(
                response,
                Constants.dashbordMonitorItemsSuffix
            )

            DashbordCompanionObject.getDashbordUpdate(
                response,
                Constants.dashbordMonitorUpdate
            )
            AllServersCompanionObject.getServerCalcul(
                response,
                Constants.heartbeatlist
            )
            StatusCompanionObject.getStatusFromResponse(response, Constants.statusListSuffix)
        }, { error ->
            NetworkResult.instance.get().postValue("3")//set error
            Log.d("error: ", error.toString())
        })
    }

}

