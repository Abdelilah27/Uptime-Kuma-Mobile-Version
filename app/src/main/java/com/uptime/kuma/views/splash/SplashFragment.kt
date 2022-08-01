package com.uptime.kuma.views.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uptime.kuma.MainActivity
import com.uptime.kuma.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        Handler(Looper.myLooper()!!).postDelayed({
            MainActivity.navController.navigate(R.id.bienvenueFragment)
        }, 2500)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}