package com.uptime.kuma.views.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentSplashBinding
import com.uptime.kuma.repository.ConnexionRepository
import com.uptime.kuma.services.ApiUtilities
import com.uptime.kuma.services.ConnexionService
import io.reactivex.android.schedulers.AndroidSchedulers


class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var webSocketService: ConnexionService
    private lateinit var splashViewModel: SplashViewModel
    private lateinit var connexionRepository: ConnexionRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSplashBinding.bind(view)


        webSocketService = ApiUtilities.getInstance(requireActivity().application)
        connexionRepository =
            ConnexionRepository(webSocketService)
        splashViewModel = ViewModelProvider(this, SplashViewModelFactory(connexionRepository))
            .get(SplashViewModel::class.java)


        observeConnection()
        binding.image.setOnClickListener {
            splashViewModel.sendQuery("40")
        }
    }

    @SuppressLint("CheckResult")
    private fun observeConnection() {
        webSocketService.observeConnection()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d("observeConnection", response.toString())
            }, { error ->
                Log.e("observeConnection", error.message.orEmpty())
            })
    }


}