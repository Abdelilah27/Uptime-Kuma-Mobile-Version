package com.uptime.kuma.views.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentSplashBinding
import com.uptime.kuma.services.ApiUtilities
import com.uptime.kuma.services.ConnexionService
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers


@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var webSocketService: ConnexionService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSplashBinding.bind(view)
//
//        val connexionService = ApiUtilities.getInstance(requireActivity().application)
//            .create(ConnexionService::class.java)

        webSocketService = ApiUtilities.getInstance(requireActivity().application)

        observeConnection()
        binding.image.setOnClickListener {
            sendQuery("40")
        }
    }

    private fun sendQuery(param: String) {
        webSocketService.sendMessage(param)
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