package com.uptime.kuma.views.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.uptime.kuma.R
import com.uptime.kuma.api.ApiUtilities
import com.uptime.kuma.api.ConnexionInterface
import com.uptime.kuma.api.NetworkResult
import com.uptime.kuma.databinding.FragmentSplashBinding
import com.uptime.kuma.repository.SplashRepository
import com.uptime.kuma.utils.Constants


class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var webSocketService: ConnexionInterface
    private lateinit var splashViewModel: SplashViewModel
    private lateinit var splashRepository: SplashRepository

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSplashBinding.bind(view)

        //Setup connexion
        webSocketService = ApiUtilities.getInstance(requireActivity().application)
        splashRepository = SplashRepository(webSocketService)
        splashViewModel = ViewModelProvider(
            this,
            SplashViewModelFactory(splashRepository)
        )[SplashViewModel::class.java]

        //Observe data changes
        splashViewModel.data.subscribe({ response ->
            Log.d("observeConnection", response.toString())
            //Success Connexion
            if (response.toString().contains(Constants.successConnexion)) {
                handleSuccessResponse()
            }
            //Failed to connect to the server
            else if (response.toString().contains(Constants.unSuccessConnexion)) {
                handleFailureResponse()
            }
            //Other response
            else {
                handleErrorResponse()
            }
            Log.d("TAG", "onViewCreated: " + NetworkResult.instance.get())
        }, { error ->
            handleErrorResponse()
        })

        binding.image.setOnClickListener {
            splashViewModel.sendQuery("40")
        }
    }

    private fun handleErrorResponse() {
        //Set error connexion response
        NetworkResult().set("3")
    }

    private fun handleFailureResponse() {
        //Set failure connexion response
        NetworkResult().set("2")
    }

    private fun handleSuccessResponse() {
        //Set success connexion response
        NetworkResult().set("1")
    }


}