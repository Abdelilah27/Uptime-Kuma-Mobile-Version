package com.uptime.kuma.api

object NetworkStatus {
    // 0 -> Connexion opened
    // 1 -> Success response
    // 2 -> failure response
    // 3 -> error response
    // 4 -> closed response
    // 5 -> resend response
    // 6 -> no response after a delay
    var networkStatus = "0"
}