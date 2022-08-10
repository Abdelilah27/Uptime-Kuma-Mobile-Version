package com.uptime.kuma.views.status

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tinder.scarlet.WebSocket
import com.uptime.kuma.models.status.Status
import com.uptime.kuma.utils.Constants
import org.json.JSONArray
import org.json.JSONObject

object StatusCompanionObject {
    private val status: ArrayList<Status> = ArrayList()
    private val _statusLiveData=MutableLiveData<ArrayList<Status>>()
    private val statusLiveData:LiveData<ArrayList<Status>>
    get() = _statusLiveData

    fun getStatusFromResponse(response: WebSocket.Event?, suffix: String){
        if(response.toString().contains(suffix)){
            val responseAfter = response.toString().substringAfter(
                Constants.statusListSuffix
            )
            val responseEnd="["+responseAfter
            val jsonArray = JSONArray(responseEnd)

            for (i in 0 until jsonArray.length()) {
                val json = jsonArray.getJSONObject(i)
                Log.d("json",json.toString()) //i don't understand


            }

        }
    }



}