package com.uptime.kuma.views.dashbord

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uptime.kuma.models.CalculDashboardItem

class DashbordViewModel : ViewModel() {
    private val _calculItemLiveData = MutableLiveData<ArrayList<CalculDashboardItem>>()
    val calculItemLiveData: LiveData<ArrayList<CalculDashboardItem>>
        get() = _calculItemLiveData

    val calculItemList: ArrayList<CalculDashboardItem> = ArrayList()

    init {
        calculItemList.add(CalculDashboardItem("En ligne", "0"))
        calculItemList.add(CalculDashboardItem("Hors ligne", "0"))
        calculItemList.add(CalculDashboardItem("Inconnu", "0"))
        calculItemList.add(CalculDashboardItem("En Pause", "0"))
        _calculItemLiveData.postValue(calculItemList)
        calculStatistics()
    }

//    private fun getList(title: String): ArrayList<CalculDashboardItem> {
//        val calculItemListTemp: ArrayList<CalculDashboardItem> = ArrayList()
//        calculItemList.forEach {
//            if (it.title == title) {
//                calculItemListTemp.add(it)
//            }
//        }
//        Log.d("HHH", calculItemListTemp.toString())
//        return calculItemListTemp
//    }

    fun calculStatistics() {
        for (i in 0 until DashbordCompanionObject.newList.size) {
            when (DashbordCompanionObject.newList[i].status) {
                0 -> {
                    Log.d("AAA", "AAAA")
                }
                1 -> {
                    Log.d("BBB", "AAAA")

                }
                2 -> {
                    Log.d("CCC", "AAAA")

                }
                else -> {
                    Log.d("DDD", "AAAA")

                }
            }

        }
    }
}
