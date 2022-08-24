package com.uptime.kuma.utils

import com.uptime.kuma.models.dashboardCalcul.CalculDashboardItem


internal var CALCUL: ArrayList<CalculDashboardItem>? = null

// 0 -> Connexion opened
// 1 -> Success response
// 2 -> failure response
// 3 -> error response
// 4 -> closed response
// 5 -> resend response
// 6 -> no response after a delay
internal var NETWORKSTATUS = "0"