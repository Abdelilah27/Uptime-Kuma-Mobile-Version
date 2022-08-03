package com.uptime.kuma.views.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uptime.kuma.repository.ConnexionRepository
import kotlinx.coroutines.launch

class SplashViewModel(private val connexionRepository: ConnexionRepository) : ViewModel() {

    fun sendQuery(param: String) {
        viewModelScope.launch {
            connexionRepository.sendMessage(param)
        }
    }

}