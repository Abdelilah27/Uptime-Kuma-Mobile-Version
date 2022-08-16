package com.uptime.kuma.views.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.uptime.kuma.R
import com.uptime.kuma.api.NetworkResult
import com.uptime.kuma.databinding.FragmentSplashBinding


class SplashFragment : Fragment(R.layout.fragment_splash) {

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSplashBinding.bind(view)

        binding.apply {
            //Direction to the Bienvenue fragment when the data are retrieved
            findNavController().navigate(R.id.bienvenueFragment)

//            NetworkResult.instance.get().observe(viewLifecycleOwner, Observer {
//                if (NetworkResult.instance.get().value == "1") {
//                    progressBar.visibility = View.GONE
//                    findNavController().navigate(R.id.bienvenueFragment)
//                }
//            })
        }
    }


}