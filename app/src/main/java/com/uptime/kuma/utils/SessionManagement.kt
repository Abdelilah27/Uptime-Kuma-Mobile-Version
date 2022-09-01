package com.uptime.kuma.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManagement {
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    var con: Context
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
        val KEY_USERNAME = "key_user"
        val KEY_PASS = "key_pass"
    }

    fun creatLoginSocket(
        socket: String,
        username: String = null.toString(),
        pass: String = null.toString()
    ) {
        editor.putBoolean(IS_LOGGED, true)
        editor.putString(KEY_SOCKET, socket)
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_PASS, pass)
        editor.commit()
    }

    fun checkIsLogged(): Boolean {
        return pref.getBoolean(IS_LOGGED, false)
    }

    fun getSocket(): String {
        return pref.getString(KEY_SOCKET, "null")!!
    }

    fun getUsername(): String {
        return pref.getString(KEY_USERNAME, "null")!!
    }

    fun getPass(): String {
        return pref.getString(KEY_PASS, "null")!!
    }

    fun logOut() {
        editor.clear()
        editor.commit()
    }


}