package com.uptime.kuma.models.monitor

import com.uptime.kuma.models.monitorStatus.MonitorStatus
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem

data class Monitor(
    val accepted_statuscodes: ArrayList<String>? = null,
    val active: Int = 1,
    val authDomain: Any? = null,
    val authMethod: Any? = null,
    val authWorkstation: Any? = null,
    val basic_auth_pass: Any? = null,
    val basic_auth_user: Any? = null,
    val body: Any? = null,
    val databaseConnectionString: Any? = null,
    val databaseQuery: Any? = null,
    val dns_last_result: Any? = null,
    val dns_resolve_server: String = "1.1.1.1",
    val dns_resolve_type: String = "A",
    val expiryNotification: Boolean = true,
    val headers: Any? = null,
    val hostname: Any? = null,
    val id: Int,
    val ignoreTls: Boolean = false,
    val interval: Int = 60,
    val keyword: Any? = null,
    val maxredirects: Int = 10,
    val maxretries: Int = 5,
    val method: String = "GET",
    val mqttPassword: Any? = null,
    val mqttSuccessMessage: Any? = null,
    val mqttTopic: Any? = null,
    val mqttUsername: Any? = null,
    val name: String,
    //TODO
    val notificationIDList: NotificationIDList? = null,
    val port: Any? = null,
    val proxyId: Any? = null,
    val pushToken: Any? = null,
    val retryInterval: Int = 60,
    val tags: List<Any>? = null,
    val type: String = "http",
    val upsideDown: Boolean = false,
    val url: String,
    val weight: Int = 2000,

    // MonitorStatus
    val monitorStatus: ArrayList<MonitorStatusItem> ?= null
)