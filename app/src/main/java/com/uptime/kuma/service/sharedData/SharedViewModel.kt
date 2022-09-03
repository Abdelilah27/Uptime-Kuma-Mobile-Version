package com.uptime.kuma.service.sharedData

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinder.scarlet.WebSocket
import com.uptime.kuma.R
import com.uptime.kuma.models.monitor.Monitor
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem
import com.uptime.kuma.models.serverCalcul.ServerCalcul
import com.uptime.kuma.models.serverCalcul.ServerCalcul_Items
import com.uptime.kuma.models.status.Status
import com.uptime.kuma.repository.SharedRepository
import com.uptime.kuma.utils.*
import com.uptime.kuma.views.dashbord.DashboardFragment
import io.reactivex.Flowable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

open class SharedViewModel(private val sharedRepository: SharedRepository) :
    ViewModel() {
    //Dashboard Fragment
    var newList: ArrayList<MonitorStatusItem> = ArrayList()
    private val _newLiveData = MutableLiveData<ArrayList<MonitorStatusItem>>()
    val newLiveData: LiveData<ArrayList<MonitorStatusItem>>
        get() = _newLiveData
    val monitorStatusList: ArrayList<MonitorStatusItem> = ArrayList()
    private val _monitorStatusLiveData = MutableLiveData<ArrayList<MonitorStatusItem>>()
    val monitorStatusLiveData: LiveData<ArrayList<MonitorStatusItem>>
        get() = _monitorStatusLiveData

    //AllServers Fragment
    val monitors: ArrayList<Monitor> = ArrayList()
    val idMonitors: ArrayList<Int> = ArrayList()
    private val _monitorCalculLiveData = MutableLiveData<ArrayList<ServerCalcul>>()
    val monitorCalculLiveData: LiveData<ArrayList<ServerCalcul>>
        get() = _monitorCalculLiveData
    var idM = 0
    val monitorCalcul: ArrayList<ServerCalcul> = ArrayList()

    //Status Fragment
    private val statues: ArrayList<Status> = ArrayList()
    private val _statusLiveData = MutableLiveData<ArrayList<Status>>()
    val statusLiveData: LiveData<ArrayList<Status>>
        get() = _statusLiveData

    //For Notifications


    //Get Data
    val data: Flowable<WebSocket.Event>
        get() = sharedRepository!!.getData()

    //Send Data
    fun sendQuery(param: String) {
        viewModelScope.launch {
            sharedRepository!!.sendMessage(param)
        }
    }

    //To Login
    fun sendLogin(mail: String, pass: String): String {
        return "420[\"login\",{\"username\":\"$mail\",\"password\":\"$pass\"," +
                "\"token\":\"\"}]"
    }

    @SuppressLint("CheckResult")
    suspend fun handleConnexionState() {
        data.subscribe({ response ->
            //Manage Connexion State
            Handler(Looper.getMainLooper()).postDelayed({ //to show error dialog after a delay
                if (NETWORKSTATUS == "0") {
                    NETWORKSTATUS = "6"
                    _NETWORKLIVEDATA.postValue("6") //no response after a delay
                }
            }, 30000)
            if (response.toString()
                    .contains(Constants.successConnexion) && NETWORKSTATUS == "0"
            ) {
                sendQuery(Constants.dataQuery) //Send query after opening the connexion
                NETWORKSTATUS = "1" //Success response
                _NETWORKLIVEDATA.postValue("1")
            }
            if (response.toString()
                    .contains(Constants.autoLogin) && NETWORKSTATUS == "1"
            ) {
                NETWORKSTATUS = "7" //autoLogin
                _NETWORKLIVEDATA.postValue("7")
                AUTOLOGIN = 1
            }
            if (response.toString()
                    .contains(Constants.successLogin)
            ) {
                NETWORKSTATUS = "8" //success Login
                _NETWORKLIVEDATA.postValue("8")
            } else if (response.toString()
                    .contains(Constants.unSuccessLogin)
            ) {
                NETWORKSTATUS = "9" //unSuccess Login
                _NETWORKLIVEDATA.postValue("9")
            }

            if (response.toString().contains(Constants.emission)) {
                TRYNUMBER += 1
                _TRYNUMBERLIVEDATA.postValue(TRYNUMBER)
                sendQuery(Constants.dataQueryResend)
                NETWORKSTATUS = "5" //Resend response
                _NETWORKLIVEDATA.postValue("5")
            }
            if (response.toString()
                    .contains(Constants.unSuccessConnexion)
            ) {
                NETWORKSTATUS = "2" //Error response
                _NETWORKLIVEDATA.postValue("2")
            }


            CoroutineScope(Dispatchers.Main).launch {
                getMonitorsFromResponse(
                    response,
                    Constants.monitorListSuffix,
                )

                getDashbordMonitorItem(
                    response,
                    Constants.dashbordMonitorItemsSuffix
                )

                getDashbordUpdate(
                    response,
                    Constants.dashbordMonitorUpdate
                )
                getServerCalcul(
                    response,
                    Constants.heartbeatlist
                )
                getStatusFromResponse(
                    response,
                    Constants.statusListSuffix
                )
            }
//            Log.d("RES", response.toString())
        }, { error ->
            NETWORKSTATUS = "3"//set error
            _NETWORKLIVEDATA.postValue("3")
            Log.d("error: ", error.toString())
        })
    }


    //Dashboard Fragment
    fun getDashbordMonitorItem(response: WebSocket.Event?, suffix: String) {
        var monitorStatusItem: MonitorStatusItem
        if (response.toString().contains(suffix)) {
            restartTryNumber()
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
                val duration = myObject.get("duration").toString()
                val ping = myObject.get("ping").toString()

                // init MonitorStatusItem
                monitorStatusItem = MonitorStatusItem(
                    monitorID = monitorID.toInt(),
                    msg = msg,
                    status = status.toInt(),
                    time = time,
                    name = getMonitorName(monitorID.toInt()),
                    duration = duration.toInt(),
                    ping = ping
                )
                monitorStatusList.add(monitorStatusItem)
            }
            // Traverse through the first list
            newList =
                monitorStatusList.distinctBy { MonitorStatusItem -> MonitorStatusItem.monitorID } as ArrayList<MonitorStatusItem>
            _newLiveData.postValue(newList)
            monitorStatusList.sortByDescending { it.time }
            _monitorStatusLiveData.postValue(monitorStatusList)

        }
    }

    fun getMonitorName(id: Int): String {
        monitors.forEach {
            if (it.id == id) {
                return it.name!!
            }
        }
        return ""
    }

    fun getDashbordUpdate(response: WebSocket.Event?, suffix: String) {
        var monitorUpdate: MonitorStatusItem
        if (response.toString().contains(suffix)) {
            restartTryNumber()
            val customResponseAfter = response.toString().substringAfter(suffix)
            val jsonObject = JSONObject(customResponseAfter)
            val monitorID = jsonObject.get("monitorID").toString()
            val msg = jsonObject.get("msg").toString()
            val status = jsonObject.get("status").toString()
            val time = jsonObject.get("time").toString()
            val duration = jsonObject.get("duration").toString()
            val important = jsonObject.getBoolean("important")
            // init MonitorStatusItem
            monitorUpdate = MonitorStatusItem(
                monitorID = monitorID.toInt(),
                msg = msg,
                status = status.toInt(),
                time = time,
                important = important,
                name = getMonitorName(monitorID.toInt()),
                duration = duration.toInt()
            )

            val serverCalculItems = ServerCalcul_Items(
                monitor_id = monitorID.toInt(),
                msg = msg,
                status = status.toInt(),
                time = time,
                duration = duration.toInt(),
            )

            injectNewStatus(serverCalculItems)
            if (monitorUpdate.important == true) {
                newList.add(monitorUpdate)
//                newList = newList.sortedBy { it.time }.distinctBy{it->it.monitorID} as ArrayList
//                _newLiveData.postValue(newList)
                newList.sortByDescending { MonitorUpdate -> MonitorUpdate.time }
                newList =
                    newList.distinctBy { MonitorStatusItem -> MonitorStatusItem.monitorID } as ArrayList<MonitorStatusItem>
                newList.sortBy { MonitorUpdate -> MonitorUpdate.monitorID }
                newList.forEach {
                    println(it.monitorID.toString() + it.time)
                }

                newList.sortBy { MonitorUpdate -> MonitorUpdate.time }
                newList.distinctBy { MonitorUpdate -> MonitorUpdate.monitorID }
                _newLiveData.postValue(newList)
                monitorStatusList.add(monitorUpdate)
                monitorStatusList.sortByDescending { it.time }
                _monitorStatusLiveData.postValue(monitorStatusList)
                //Notification
                try {
                    val instance: UpdateData = DashboardFragment.instance
                    instance.onReceivedData(monitorUpdate)
                } catch (e: Exception) {
                    Log.d("Error :", e.message.toString())
                }
            }

        }
    }

    private fun injectNewStatus(serverCalculItems: ServerCalcul_Items) {
        val monitorId = serverCalculItems.monitor_id
        monitorCalcul.forEach {
            if (it.monitor_id == monitorId) {
                it.monitorStatus.add(serverCalculItems)
                it.monitorStatus.sortByDescending { it.time }
            }
        }
        _monitorCalculLiveData.postValue(monitorCalcul.distinctBy { it.monitor_id } as ArrayList<ServerCalcul>?)
    }


    //AllServers Fragment
    fun getServerCalcul(response: WebSocket.Event?, suffix: String) {
        var myobject: ServerCalcul_Items
        val calculitems: ArrayList<ServerCalcul_Items> = ArrayList()
        if (response.toString().contains(suffix)) {
            restartTryNumber()
            val customResponseAfter = response.toString().substringAfter(suffix)
            val customResponseBegin = "[$customResponseAfter"
            val customResponseEnd = customResponseBegin.dropLast(9)
            val customResponselast = "$customResponseEnd]"
            val jsonObject = JSONArray(customResponselast)
            val monitorList = jsonObject[1].toString()
            val monitorStatus = JSONArray(monitorList)
            for (i in 0 until monitorStatus.length()) {
                val jsonObject = JSONObject(monitorStatus[i].toString())
                val monitorID = jsonObject.get("monitor_id").toString()
                if (monitorID.toInt() !in idMonitors) {
                    idMonitors.add(monitorID.toInt())
                    idM = monitorID.toInt()
                }
                val msg = jsonObject.get("msg").toString()
                val status = jsonObject.get("status").toString()
                val time = jsonObject.get("time").toString()
                val duration = jsonObject.get("duration").toString()
                val ping = jsonObject.get("ping").toString()
//                // init object
                myobject = ServerCalcul_Items(
                    monitor_id = monitorID.toInt(),
                    msg = msg,
                    status = status.toInt(),
                    time = time,
                    duration = duration.toInt(),
                    ping = ping

                )
                //For recycler graph card
                calculitems.add(myobject)
                calculitems.sortByDescending { it.time }
            }
            monitorCalcul.add(ServerCalcul(monitor_id = idM, monitorStatus = calculitems))
            _monitorCalculLiveData.postValue(monitorCalcul)
        }
    }

    fun getMonitorsFromResponse(response: WebSocket.Event?, suffix: String) {
        var monitor: Monitor
        if (response.toString().contains(suffix)) {
            restartTryNumber()
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
                    monitor = Monitor(
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

    fun getMonitorById(id: Int): Monitor {
        monitors.forEach {
            if (it.id == id) {
                return it
            }
        }
        return monitors.get(0)
    }

    fun getStatuesServerById(id: Int): ArrayList<ServerCalcul_Items> {
        val statusV2: ArrayList<ServerCalcul_Items> = ArrayList()
        monitorCalcul.forEach { it ->
            if (it.monitor_id == id) {
                it.monitorStatus.forEach {
                    if (it.monitor_id == id) {
                        statusV2.add(it)
                    }
                }
            }
        }
        return statusV2
    }

    //Status Fragment
    fun getStatusFromResponse(response: WebSocket.Event?, suffix: String) {
        var status: Status
        if (response.toString().contains(suffix)) {
            restartTryNumber()
            val responseAfter = response.toString().substringAfter(
                Constants.statusListSuffix
            )
            val responseBefore = "{$responseAfter"
            val responseEnd = responseBefore.dropLast(3)//hade 3 dialach
            val jsonObject = JSONObject(responseEnd)
            val lastJsonObject = jsonObject.getJSONObject(
                jsonObject.names().get(jsonObject.length() - 1) as String
            )// i don't understand
            val lengthOfJsonObject = lastJsonObject.get("id") as Int
            for (i in 0 until lengthOfJsonObject + 1) {
                if (jsonObject.has(i.toString())) {
                    val json = jsonObject.getJSONObject(i.toString())
                    val customCSS = json.get("customCSS").toString()
                    val description = json.get("description").toString()
                    val domainNameList: ArrayList<Any> = ArrayList()

                    val footerText = json.get("footerText").toString()
                    val icon = R.drawable.ic_icon
                    val id = json.get("id")
                    val published = json.get("published")
                    val showPoweredBy = json.get("showPoweredBy")
                    val showTags = json.get("showTags")
                    val slug = json.get("slug").toString()
                    val theme = json.get("theme").toString()
                    val title = json.get("title").toString()

                    status = Status(
                        customCSS = customCSS,
                        description = description,
                        footerText = footerText,
                        id = id as Int,
                        icon = icon,
                        published = published as Boolean,
                        showPoweredBy = showPoweredBy as Boolean,
                        showTags = showTags as Boolean,
                        slug = slug,
                        theme = theme,
                        title = title
                    )
                    statues.add(status)

                } else {
                    continue
                }
            }
            _statusLiveData.postValue(statues)
        }
    }

    private fun restartTryNumber() {
        TRYNUMBER = 0
        _TRYNUMBERLIVEDATA.postValue(TRYNUMBER)
    }

}

