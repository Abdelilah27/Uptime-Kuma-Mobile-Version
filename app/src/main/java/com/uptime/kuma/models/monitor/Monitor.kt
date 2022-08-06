package com.uptime.kuma.models.monitor

data class Monitor(
    val accepted_statuscodes: List<String>,
    val active: Int, // !
    val authDomain: Any,
    val authMethod: Any,
    val authWorkstation: Any,
    val basic_auth_pass: Any,
    val basic_auth_user: Any,
    val body: Any,
    val databaseConnectionString: Any,
    val databaseQuery: Any,
    val dns_last_result: Any,
    val dns_resolve_server: String,
    val dns_resolve_type: String,
    val expiryNotification: Boolean,
    val headers: Any,
    val hostname: Any,
    val id: Int, // !
    val ignoreTls: Boolean,
    val interval: Int,
    val keyword: Any,
    val maxredirects: Int,
    val maxretries: Int,
    val method: String,
    val mqttPassword: Any,
    val mqttSuccessMessage: Any,
    val mqttTopic: Any,
    val mqttUsername: Any,
    val name: String, // !
    val notificationIDList: NotificationIDList,
    val port: Any,
    val proxyId: Any,
    val pushToken: Any,
    val retryInterval: Int, // !
    val tags: List<Any>,
    val type: String,
    val upsideDown: Boolean,
    val url: String, // !
    val weight: Int
)