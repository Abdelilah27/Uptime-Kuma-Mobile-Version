package com.uptime.kuma.views.dashbord

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tinder.scarlet.WebSocket
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem
import org.json.JSONArray
import org.json.JSONObject


object DashbordCompanionObject {
    //    lateinit var monitorStatusList:MonitorStatus
    var newList: ArrayList<MonitorStatusItem> = ArrayList()
    val monitorStatusList: ArrayList<MonitorStatusItem> = ArrayList()
    private val _monitorStatusLiveData = MutableLiveData<ArrayList<MonitorStatusItem>>()
    val monitorStatusLiveData: LiveData<ArrayList<MonitorStatusItem>>
        get() = _monitorStatusLiveData

    fun getDashbordMonitorItem(response: WebSocket.Event?, suffix: String) {
        //
        if (response.toString().contains(suffix)) {
            val customResponseAfter = response.toString().substringAfter(suffix)
            //add [ at the beginning of the response
            val customResponseBegin = "[$customResponseAfter"
            //delete )) at the end of the response
            val customResponseEnd = customResponseBegin.dropLast(9)
            val customResponselast = "$customResponseEnd]"
            val jsonObject = JSONArray(customResponselast)
            val monitorList = jsonObject[1].toString()
            val monitorStatus = JSONArray(monitorList)
            for (i in 0 until monitorStatus.length()) {
                val myObject = JSONObject(monitorStatus[i].toString())
                val monitorID = myObject.get("monitorID").toString()
                val msg = myObject.get("msg").toString()
                val status = myObject.get("status").toString()
                val time = myObject.get("time").toString()
                // init MonitorStatusItem
                val monitorStatusItem = MonitorStatusItem(
                    monitorID = monitorID.toInt(),
                    msg = msg,
                    status = status.toInt(),
                    time = time
                )
                monitorStatusList.add(monitorStatusItem)
            }
            // Traverse through the first list
            newList =
                monitorStatusList.distinctBy { MonitorStatusItem -> MonitorStatusItem.monitorID } as ArrayList<MonitorStatusItem>
            monitorStatusList.sortByDescending { it.time }
//            Log.d("TAG", monitorStatusList.toString())
            _monitorStatusLiveData.postValue(monitorStatusList)

        }



    }


}