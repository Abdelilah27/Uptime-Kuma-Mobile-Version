package com.uptime.kuma.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.uptime.kuma.models.dashboardCalcul.CalculDashboardItem


internal var CALCUL: ArrayList<CalculDashboardItem>? = null

// 0 -> Connexion opened
// 1 -> Success response
// 2 -> failure response
// 3 -> error response
// 4 -> closed response
// 5 -> resend response
// 6 -> no response after a delay
// 7 -> autoLogin
// 8 -> success Login
// 9 -> unSuccess Login
internal var NETWORKSTATUS = "0"
internal val _NETWORKLIVEDATA = MutableLiveData<String>()
internal val NETWORKLIVEDATA: LiveData<String>
    get() = _NETWORKLIVEDATA

internal var TRYNUMBER = 0
internal val _TRYNUMBERLIVEDATA = MutableLiveData<Int>()
internal val TRYNUMBERLIVEDATA: LiveData<Int>
    get() = _TRYNUMBERLIVEDATA

internal var AUTOLOGIN = 0

