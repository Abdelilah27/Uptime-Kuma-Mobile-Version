package com.uptime.kuma.views.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentSplashBinding
import com.uptime.kuma.repository.SplashRepository
import com.uptime.kuma.services.ApiUtilities
import com.uptime.kuma.services.ConnexionInterface


class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var webSocketService: ConnexionInterface
    private lateinit var splashViewModel: SplashViewModel
    private lateinit var splashRepository: SplashRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSplashBinding.bind(view)

        webSocketService = ApiUtilities.getInstance(requireActivity().application)
        splashRepository =
            SplashRepository(webSocketService)
        splashViewModel = ViewModelProvider(this, SplashViewModelFactory(splashRepository))
            .get(SplashViewModel::class.java)

        observeConnection()
        binding.image.setOnClickListener {
            splashViewModel.sendQuery("40")
        }
    }

    @SuppressLint("CheckResult")
    private fun observeConnection() {
        splashViewModel.data.subscribe({ response ->
            Log.d("observeConnection", response.toString())
        }, { error ->
            Log.e("observeConnection", error.message.orEmpty())
        })
    }


}