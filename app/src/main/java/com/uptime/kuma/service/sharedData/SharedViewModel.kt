package com.uptime.kuma.service.sharedData

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import java.util.*

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
                    if (response.toString()
                            .contains(Constants.successConnexion) && NetworkResult
                            .instance.get().value == "0"
                    ) {
                        sendQuery(Constants.dataQuery)
                        NetworkResult.instance.get().postValue("1") //Success response
                    }
                })
            }
            DashbordCompanionObject.getDashbordMonitorItem(
                response,
                Constants.dashbordMonitorItemsSuffix

            )
            AllServersCompanionObject.getMonitorsFromResponse(
                response,
                Constants.monitorListSuffix,
            )
            StatusCompanionObject.getStatusFromResponse(response, Constants.statusListSuffix)
//            Log.d("TAG", response.toString())
        }, { error ->
            NetworkResult.instance.get().postValue("3")//set error
            Log.d("error: ", error.toString())
        })
    }

    //setup language
    fun setAppLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }


}

