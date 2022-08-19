package com.uptime.kuma.views.monitorsList

import android.util.Log
import com.tinder.scarlet.WebSocket
import com.uptime.kuma.models.monitor.Monitor
import com.uptime.kuma.models.serverCalcul.ServerCalcul
import com.uptime.kuma.utils.Constants
import com.uptime.kuma.views.dashbord.DashbordCompanionObject
import org.json.JSONArray
import org.json.JSONObject

object AllServersCompanionObject {
    val monitors: ArrayList<Monitor> = ArrayList()
//    private val _monitorLiveData = MutableLiveData<ArrayList<Monitor>>()
//    val monitorLiveData: LiveData<ArrayList<Monitor>>
//        get() = _monitorLiveData
    val monitorCalcul : ArrayList<ServerCalcul> = ArrayList()

    //get Monitors List
    fun getMonitorsFromResponse(response: WebSocket.Event?, suffix: String) {
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
            //last Json Object
            val lastJsonObject = jsonObject.getJSONObject(
                jsonObject.names().get(jsonObject.length() - 1) as String
            )
            //get id of last json object as length of json object
            val lengthOfJsonObject = lastJsonObject.get("id") as Int
            for (i in 0 until lengthOfJsonObject + 1) {
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
                        accepted_statuscodes = accepted_statuscodes,
                    )
                    //add monitors to ArrayList
                    monitors.add(monitor)
                } else {
                    continue
                }
            }
//            Log.d("ZZZ", monitors.toString())
            //add ArrayList to MutableLiveData
//            _monitorLiveData.postValue(monitors)
        }
    }

    fun getServerCalcul(response: WebSocket.Event?, suffix: String) {
        if (response.toString().contains(suffix)){
            val customResponseAfter = response.toString().substringAfter(suffix)
            val customResponseBegin = "[$customResponseAfter"
            val customResponseEnd = customResponseBegin.dropLast(9)
            val customResponselast = "$customResponseEnd]"
            val jsonObject = JSONArray(customResponselast)
//            Log.d("getServerCalcul", jsonObject[1].toString())
            val monitorList = jsonObject[1].toString()
            val monitorStatus = JSONArray(monitorList)
            for (i in 0 until monitorStatus.length()){
                val jsonObject = JSONObject(monitorStatus[i].toString())
                val monitorID = jsonObject.get("monitor_id").toString()
                val msg = jsonObject.get("msg").toString()
                val status = jsonObject.get("status").toString()
                val time = jsonObject.get("time").toString()
                // init object
                val myobject=ServerCalcul(
                    monitor_id = monitorID.toInt(),
                    msg = msg,
                    status = status.toInt(),
                    time = time
                )
                //For recycler graph card
                monitorCalcul.add(myobject)
                println(myobject.monitor_id)
            }
        }


    }



}