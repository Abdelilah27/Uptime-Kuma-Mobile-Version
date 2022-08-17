package com.uptime.kuma.views.dashbord

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tinder.scarlet.WebSocket
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem
import com.uptime.kuma.views.dashbord.utils.UpdateData
import org.json.JSONArray
import org.json.JSONObject


object DashbordCompanionObject {
    //    lateinit var monitorStatusList:MonitorStatus
    var sharedNewRecievedData: UpdateData = DashboardFragment.instance
    var newList: ArrayList<MonitorStatusItem> = ArrayList()
    val monitorStatusList: ArrayList<MonitorStatusItem> = ArrayList()
    val monitorUpdateList: ArrayList<MonitorStatusItem> = ArrayList()
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

    fun getDashbordUpdate(response: WebSocket.Event?, suffix: String) {
        var list: ArrayList<MonitorStatusItem> = ArrayList()
        if (response.toString().contains(suffix)) {
            val customResponseAfter = response.toString().substringAfter(suffix)
            val jsonObject = JSONObject(customResponseAfter)
            Log.d("filter2", jsonObject.toString())
            val monitorID = jsonObject.get("monitorID").toString()
            val msg = jsonObject.get("msg").toString()
            val status = jsonObject.get("status").toString()
            val time = jsonObject.get("time").toString()
            val important = jsonObject.getBoolean("important")
            // init MonitorStatusItem
            val monitorUpdate = MonitorStatusItem(
                monitorID = monitorID.toInt(),
                msg = msg,
                status = status.toInt(),
                time = time,
                important = important
            )
            if(monitorUpdate.important == true)
            {
                monitorStatusList.add(monitorUpdate)
                monitorStatusList.sortByDescending { it.time }
                _monitorStatusLiveData.postValue(monitorStatusList)
            }

        }


    }


}