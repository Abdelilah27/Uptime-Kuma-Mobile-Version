package com.uptime.kuma.utils

import android.content.Context
import android.content.SharedPreferences

class LanguageSettings(context: Context){

        var sharedPreferences: SharedPreferences
        var editor: SharedPreferences.Editor
        var language: String?
            get() = sharedPreferences.getString("KEY_LANGUAGE", "")
            set(language) {
                editor.putString("KEY_LANGUAGE", language)
                editor.commit()
            }

        init {
            sharedPreferences = context.getSharedPreferences("AppKey", 0)
            editor = sharedPreferences.edit()
            editor.apply()
        }

}