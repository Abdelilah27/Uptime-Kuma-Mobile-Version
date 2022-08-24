package com.uptime.kuma.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Process
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.uptime.kuma.models.dashboardCalcul.CalculDashboardItem
import com.uptime.kuma.views.mainActivity.MainActivity


internal var CALCUL: ArrayList<CalculDashboardItem>? = null

// 0 -> Connexion opened
// 1 -> Success response
// 2 -> failure response
// 3 -> error response
// 4 -> closed response
// 5 -> resend response
// 6 -> no response after a delay
internal var NETWORKSTATUS = "0"
internal val _NETWORKLIVEDATA = MutableLiveData<String>()
internal val NETWORKLIVEDATA: LiveData<String>
    get() = _NETWORKLIVEDATA
