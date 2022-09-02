package com.uptime.kuma.views.splash

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.uptime.kuma.R
import com.uptime.kuma.utils.NETWORKLIVEDATA
import com.uptime.kuma.utils.SessionManagement
import com.uptime.kuma.views.mainActivity.MainActivity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class SplashFragment : Fragment(R.layout.fragment_splash), CoroutineScope {
    lateinit var sessionManagement: SessionManagement

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManagement = SessionManagement(requireContext())
        //redirection to the dashboard fragment
        if (sessionManagement.checkIsLogged()) {
            //setup connexion
            (activity as MainActivity).setUpConnexion(sessionManagement.getSocket())
            //case1: autologin without username or pass
            if (sessionManagement.getUsername() == null.toString() && sessionManagement.getPass()
                == null.toString()
            ) {
                findNavController().navigate(R.id.mainFragment)
            } else {
                NETWORKLIVEDATA.observe(viewLifecycleOwner, Observer {
                    when (it) {
                        "1" -> {
                            //case2: No autologin, require username and pass
                            if (sessionManagement.getUsername() != null.toString() && sessionManagement.getPass() != null.toString()
                            ) {
                                MainActivity.sharedViewModel.sendQuery(
                                    MainActivity.sharedViewModel.sendLogin
                                        (
                                        sessionManagement.getUsername(),
                                        sessionManagement.getPass()
                                    )
                                )
                                findNavController().navigate(R.id.mainFragment)
                            }
                        }
                    }
                })
            }
        } else {
            launch {
                delay(5000)
                withContext(Dispatchers.Main) {
                    if (onCommencerFinished()) {
                        MainActivity.navController.navigate(R.id.loginFragment)
                    } else {
                        findNavController().navigate(R.id.bienvenueFragment)
                    }
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