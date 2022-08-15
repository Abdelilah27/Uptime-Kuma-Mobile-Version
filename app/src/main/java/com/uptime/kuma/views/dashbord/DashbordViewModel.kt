package com.uptime.kuma.views.dashbord

import androidx.lifecycle.ViewModel

class DashbordViewModel : ViewModel() {
    var online: Int = 0
    var offline: Int = 0
    var unknown: Int = 0
    var pause: Int = 0

    fun calculStatistics() {
        for (i in 0 until DashbordCompanionObject.newList.size) {
            when (DashbordCompanionObject.newList[i].status) {
                0 -> offline += 1
                1 -> online += 1
                2 -> pause += 1
                else -> {
                    unknown += 1
                }
            }

        }
    }
}