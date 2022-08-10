package com.uptime.kuma.models.status

data class Status(
    val customCSS: String?=null,
    val description: Any?=null,
    val domainNameList: List<Any>?=null,
    val footerText: Any?=null,
    val icon: Int?=null,
    val id: Int=1,
    val published: Boolean=false,
    val showPoweredBy: Boolean=false,
    val showTags: Boolean=false,
    val slug: String?=null,
    val theme: String?=null,
    val title: String?=null
)