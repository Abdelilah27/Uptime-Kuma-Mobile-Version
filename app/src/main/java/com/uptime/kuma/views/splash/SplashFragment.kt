package com.uptime.kuma.views.splash

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentSplashBinding
import com.uptime.kuma.views.mainActivity.MainActivity


class SplashFragment : Fragment(R.layout.fragment_splash) {

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSplashBinding.bind(view)

        binding.apply {
            Handler(Looper.myLooper()!!).postDelayed({
                //Direction to the Bienvenue fragment when the data are retrieved
                if (onCommencerFinished())
                    MainActivity.navController.navigate(R.id.loginFragment)
                else
                    findNavController().navigate(R.id.bienvenueFragment)
            }, 700)

        }
    }

    private fun onCommencerFinished(): Boolean {
        val sharedpref = requireActivity().getSharedPreferences("Bienvenue", Context.MODE_PRIVATE)
        return sharedpref.getBoolean("Finished", false)
    }

}