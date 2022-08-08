package com.uptime.kuma.utils

import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

//ces deux fonctions sont ajoutés pour rendre le title bar transparent
fun setFullScreen(window: Window) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

fun lightStatusBar(window: Window, isLight: Boolean = true) {
    val wic = WindowInsetsControllerCompat(window, window.decorView)
    wic.isAppearanceLightStatusBars = isLight
}