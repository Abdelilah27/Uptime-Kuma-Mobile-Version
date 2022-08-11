package com.uptime.kuma.views.dashbord

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tinder.scarlet.WebSocket
import com.uptime.kuma.models.monitor.Monitor
import com.uptime.kuma.models.monitorStatus.MonitorStatus
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem
import org.json.JSONArray
import org.json.JSONObject

object DashbordCompanionObject {
//    lateinit var monitorStatusList:MonitorStatus
    private val monitorStatusList:ArrayList<MonitorStatusItem> = ArrayList()
    private  val _monitorStatusLiveData= MutableLiveData<ArrayList<MonitorStatusItem>>()
    val monitorStatusLiveData: LiveData<ArrayList<MonitorStatusItem>>
    get() = _monitorStatusLiveData
    fun getDashbordMonitorItem(response: WebSocket.Event?, suffix: String){
        //
        if (response.toString().contains(suffix)){
            val customResponseAfter = response.toString().substringAfter(suffix)
            //add [ at the beginning of the response
            val customResponseBegin = "[$customResponseAfter"
            //delete )) at the end of the response
            val customResponseEnd = customResponseBegin.dropLast(9)
            val customResponselast = "$customResponseEnd]"
//            Log.d("TAG", customResponselast)

////            Log.d("fgfg", cuscustomResponseEnd)
//            Log.d("DonMhammed", "\n")
//
            val jsonObject = JSONArray(customResponselast)
            val monitorList = jsonObject[1].toString()
            val monitorStatus = JSONArray(monitorList)
//            val monitorStatusItem=monitorStatus[1]
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

//                Log.d("TAG1", "HHH1   "+myObject.get("status"))
//                Log.d("TAG2","HHH2"+monitorStatus[i].toString() )
            }

//            Log.d("TAG", monitorStatusList.toString())
            _monitorStatusLiveData.postValue(monitorStatusList)
        }
    }

//   suspend fun filterMonitorstatus(id: Int):ArrayList<MonitorStatusItem> {
//
//        val filtredList:ArrayList<MonitorStatusItem> = ArrayList()
////        filtredList.clear()
////        monitorStatusList.forEach{
////            if(it.monitorID == id && !it.equals("[]")){
////                filtredList.add(it)
////                Log.d("qsdqsd", it.toString())
////            }
////        }
//        for (i in 0 until monitorStatusList.size)
//        {
//            if (monitorStatusList[i].monitorID==id){
//                filtredList.add(monitorStatusList[i])
//            }
//        }
////        if(!filtredList.isEmpty())
//
//       return filtredList
////        Log.d("TAG", filtredList.toString())
//
//    }
}