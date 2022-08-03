package com.uptime.kuma.views.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uptime.kuma.repository.SplashRepository

class SplashViewModelFactory(private val splashRepository: SplashRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(splashRepository) as T
    }
}