package com.uptime.kuma.views.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentSplashBinding
import com.uptime.kuma.views.mainActivity.MainActivity


class SplashFragment : Fragment(R.layout.fragment_splash) {
//    private lateinit var splashViewModel: SplashViewModel
//    private lateinit var splashRepository: SplashRepository

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSplashBinding.bind(view)

        binding.image.setOnClickListener {
//            MainActivity.sharedViewModel.sendQuery("40")
            findNavController().navigate(R.id.bienvenueFragment)
        }
    }

}