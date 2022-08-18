package com.uptime.kuma.views.dashbord

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tinder.scarlet.WebSocket
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem
import com.uptime.kuma.views.monitorsList.AllServersCompanionObject
import com.uptime.kuma.utils.UpdateData
import com.uptime.kuma.models.monitorUpdate.MonitorUpdate
import org.json.JSONArray
import org.json.JSONObject


object DashbordCompanionObject {
    //    lateinit var monitorStatusList:MonitorStatus
    var newList: ArrayList<MonitorStatusItem> = ArrayList()
    private val _newLiveData = MutableLiveData<ArrayList<MonitorStatusItem>>()
    val newLiveData: LiveData<ArrayList<MonitorStatusItem>>
        get() = _newLiveData
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
                    time = time,
                    name = getMonitorName(monitorID.toInt())

                )
                monitorStatusList.add(monitorStatusItem)
            }
            // Traverse through the first list

            newList =
                monitorStatusList.distinctBy { MonitorStatusItem -> MonitorStatusItem.monitorID } as ArrayList<MonitorStatusItem>
            _newLiveData.postValue(newList)
            monitorStatusList.sortByDescending { it.time }

//            Log.d("TAG", monitorStatusList.toString())
            _monitorStatusLiveData.postValue(monitorStatusList)

        }

    }


    fun getMonitorName(id:Int):String{
        AllServersCompanionObject.monitors.forEach {
            if(it.id==id){
                return it.name
            }
        }
        return ""

    }

    fun getDashbordUpdate(response: WebSocket.Event?, suffix: String) {
        var list: ArrayList<MonitorStatusItem> = ArrayList()
        if (response.toString().contains(suffix)) {
            val customResponseAfter = response.toString().substringAfter(suffix)
            val jsonObject = JSONObject(customResponseAfter)
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
                important = important,
                name = getMonitorName(monitorID.toInt())
            )
            if (monitorUpdate.important == true) {
                newList.add(monitorUpdate)
//                newList = newList.sortedBy { it.time }.distinctBy{it->it.monitorID} as ArrayList
//                _newLiveData.postValue(newList)
                 newList.sortByDescending { MonitorUpdate ->MonitorUpdate.time}
                newList= newList.distinctBy { MonitorStatusItem -> MonitorStatusItem.monitorID } as ArrayList<MonitorStatusItem>
                newList.sortBy { MonitorUpdate ->MonitorUpdate.monitorID}
                newList.forEach {
                    println(it.monitorID.toString()+it.time)
                }

                newList.sortBy { MonitorUpdate -> MonitorUpdate.time }
                newList.distinctBy { MonitorUpdate -> MonitorUpdate.monitorID }
                _newLiveData.postValue(newList)
                monitorStatusList.add(monitorUpdate)
                monitorStatusList.sortByDescending { it.time }
                _monitorStatusLiveData.postValue(monitorStatusList)
            }

        }


    }


}