package com.uptime.kuma.utils

import android.content.Context
import android.content.SharedPreferences

//For dark mode
class SaveData(context: Context) {
    var sharedPreferences: SharedPreferences
    var editor: SharedPreferences.Editor
    var lightMode: String?
        get() = sharedPreferences.getString("KEY_MODE", "")
        set(lightMode) {
            editor.putString("KEY_MODE", lightMode)
            editor.commit()
        }

    init {
        sharedPreferences = context.getSharedPreferences("AppKey", 0)
        editor = sharedPreferences.edit()
        editor.apply()
    }
}