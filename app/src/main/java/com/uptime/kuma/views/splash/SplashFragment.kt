package com.uptime.kuma.views.splash

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uptime.kuma.R
import com.uptime.kuma.views.mainActivity.MainActivity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class SplashFragment : Fragment(R.layout.fragment_splash), CoroutineScope {

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val binding = FragmentSplashBinding.bind(view)

        launch {
            delay(500)
            withContext(Dispatchers.Main) {
                if (onCommencerFinished())
                    MainActivity.navController.navigate(R.id.loginFragment)
                else {
                    findNavController().navigate(R.id.bienvenueFragment)
                }
            }
        }
    }

    private fun onCommencerFinished(): Boolean {
        val sharedpref = requireActivity().getSharedPreferences("Bienvenue", Context.MODE_PRIVATE)
        return sharedpref.getBoolean("Finished", false)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

}