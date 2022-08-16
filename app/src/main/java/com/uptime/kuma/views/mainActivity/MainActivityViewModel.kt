package com.uptime.kuma.views.mainActivity

import android.content.Context
import androidx.lifecycle.ViewModel
import java.util.*

class MainActivityViewModel : ViewModel() {
    //setup language
    fun setAppLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}