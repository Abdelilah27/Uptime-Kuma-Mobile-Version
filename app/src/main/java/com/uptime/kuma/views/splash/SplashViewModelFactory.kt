package com.uptime.kuma.views.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uptime.kuma.repository.ConnexionRepository

class SplashViewModelFactory(private val connexionRepository: ConnexionRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(connexionRepository) as T
    }
}