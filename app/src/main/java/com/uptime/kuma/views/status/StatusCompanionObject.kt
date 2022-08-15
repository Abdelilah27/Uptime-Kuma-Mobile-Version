package com.uptime.kuma.views.status

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tinder.scarlet.WebSocket
import com.uptime.kuma.R
import com.uptime.kuma.models.status.Status
import com.uptime.kuma.utils.Constants
import org.json.JSONObject

object StatusCompanionObject {
    private val statues: ArrayList<Status> = ArrayList()
    private val _statusLiveData = MutableLiveData<ArrayList<Status>>()
    val statusLiveData: LiveData<ArrayList<Status>>
        get() = _statusLiveData

    fun getStatusFromResponse(response: WebSocket.Event?, suffix: String) {
        if (response.toString().contains(suffix)) {
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
            for (i in 0 until lengthOfJsonObject +1) {
                if (jsonObject.has(i.toString())) {
                    val json = jsonObject.getJSONObject(i.toString())
                    val customCSS=json.get("customCSS").toString()
                    val description=json.get("description").toString()
                    val domainNameList:ArrayList<Any> = ArrayList()

                    val footerText=json.get("footerText").toString()
                    val icon=R.drawable.ic_icon
                    val id=json.get("id")
                    val published=json.get("published")
                    val showPoweredBy=json.get("showPoweredBy")
                    val showTags=json.get("showTags")
                    val slug=json.get("slug").toString()
                    val theme=json.get("theme").toString()
                    val title = json.get("title").toString()

                    val status=Status(
                        customCSS=customCSS,
                        description=description,
                        footerText=footerText,
                        id = id as Int,
                        icon=icon ,
                        published = published as Boolean,
                        showPoweredBy = showPoweredBy as Boolean,
                        showTags = showTags as Boolean,
                        slug = slug,
                        theme = theme,
                        title = title
                    )
                    statues.add(status)

                }
                else{
                    continue
                }
            }
            _statusLiveData.postValue(statues)
        }
    }
}