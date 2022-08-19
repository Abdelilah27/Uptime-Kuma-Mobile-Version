package com.uptime.kuma.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManagement {
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var con: Context
    var PRIVATE_MODE: Int = 0

    constructor(con: Context) {
        this.con = con
        pref = con.getSharedPreferences(PREF_SOCKET, PRIVATE_MODE)
        editor = pref.edit()
    }

    companion object {
        val PREF_SOCKET = "socket_preference"
        val IS_LOGGED = "isLogged"
        val KEY_SOCKET = "key_socket"
    }

    fun creatLoginSocket(socket: String) {
        editor.putBoolean(IS_LOGGED, true)
        editor.putString(KEY_SOCKET, socket)
        editor.commit()
    }

    fun checkIsLogged(): Boolean {
        return pref.getBoolean(IS_LOGGED, false)
    }

    fun getSocket(): String {
        return pref.getString(KEY_SOCKET, "null")!!
    }

    fun logOut() {
        editor.clear()
        editor.commit()
    }


}