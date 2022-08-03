package com.uptime.kuma.views.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uptime.kuma.repository.SplashRepository
import kotlinx.coroutines.launch

class SplashViewModel(private val splashRepository: SplashRepository) : ViewModel() {

    fun sendQuery(param: String) {
        viewModelScope.launch {
            splashRepository.sendMessage(param)
        }
    }

}