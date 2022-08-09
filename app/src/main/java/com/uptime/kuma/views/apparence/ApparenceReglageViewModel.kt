package com.uptime.kuma.views.apparence

import android.content.Context

import androidx.lifecycle.ViewModel
import java.util.*


class ApparenceReglageViewModel(): ViewModel() {

    fun setAppLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }


}