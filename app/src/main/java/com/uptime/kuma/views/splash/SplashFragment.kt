package com.uptime.kuma.views.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tinder.scarlet.Scarlet
import com.uptime.kuma.R
import com.uptime.kuma.api.ApiUtilities
import com.uptime.kuma.api.ConnexionInterface
import com.uptime.kuma.api.NetworkResult
import com.uptime.kuma.databinding.FragmentSplashBinding
import com.uptime.kuma.service.sharedData.SharedRepository
import com.uptime.kuma.service.sharedData.SharedViewModel
import com.uptime.kuma.service.sharedData.SharedViewModelFactory


class SplashFragment : Fragment(R.layout.fragment_splash) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var scarlet: Scarlet
        lateinit var webSocketService: ConnexionInterface
        lateinit var sharedViewModel: SharedViewModel
    }

    private lateinit var sharedRepository: SharedRepository

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSplashBinding.bind(view)

        //Setup and create connexion
        scarlet = ApiUtilities.provideScarlet(requireActivity().application)
        webSocketService = ApiUtilities.getInstance(scarlet)

        //Service Shared Data
        sharedRepository = SharedRepository(webSocketService)
        sharedViewModel = ViewModelProvider(
            this,
            SharedViewModelFactory(sharedRepository)
        )[SharedViewModel::class.java]

        sharedViewModel.handleConnexionState(this, lifecycleScope = lifecycleScope)

        binding.apply {
            //Direction to the Bienvenue fragment when the data are retrieved
            NetworkResult.instance.get().observe(viewLifecycleOwner, Observer {
                if (NetworkResult.instance.get().value == "1") {
                    progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.bienvenueFragment)
                }
            })
        }
    }


}