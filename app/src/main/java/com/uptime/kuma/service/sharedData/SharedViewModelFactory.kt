package com.uptime.kuma.service.sharedData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uptime.kuma.repository.SharedRepository

class SharedViewModelFactory(private val sharedRepository: SharedRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SharedViewModel(sharedRepository) as T
    }
}