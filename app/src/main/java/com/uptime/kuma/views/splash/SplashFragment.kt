package com.uptime.kuma.views.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentSplashBinding
import com.uptime.kuma.views.mainActivity.MainActivity


class SplashFragment : Fragment(R.layout.fragment_splash) {
    //shared View Model
//    private lateinit var splashViewModel: SplashViewModel
//    private lateinit var splashRepository: SplashRepository

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSplashBinding.bind(view)

        MainActivity.sharedViewModel.data.subscribe({ response ->
            Log.d("response: ", response.toString())
        }, { error ->
            Log.d("error: ", error.toString())
        })

        binding.image.setOnClickListener {
            MainActivity.sharedViewModel.sendQuery("40")
        }
    }

}