package com.uptime.kuma.views.splash

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentSplashBinding
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
        val binding = FragmentSplashBinding.bind(view)
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
                                //when connexion state is opened, send the login
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
                        "2", "3", "6", "9" -> {
                            binding.progressBar.visibility = View.GONE
                            showErrorDialog()
                        }
                    }
                })
            }
        } else {
            launch {
                delay(6000)
                withContext(Dispatchers.Main) {
                    if (onCommencerFinished()) {
                        MainActivity.navController.navigate(R.id.bienvenueFragment)
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

    private fun showErrorDialog() {
        val builder =
            AlertDialog.Builder(requireContext(), com.uptime.kuma.R.style.AlertDialogTheme)
                .create()
        val view = layoutInflater.inflate(com.uptime.kuma.R.layout.layout_error_dialog, null)
        val button = view.findViewById<Button>(com.uptime.kuma.R.id.buttonAction)
        builder.setView(view)
        (view.findViewById<View>(com.uptime.kuma.R.id.textTitle) as TextView).text =
            resources.getString(com.uptime.kuma.R.string.title_error_dialog)
        (view.findViewById<View>(com.uptime.kuma.R.id.textMessage) as TextView).text =
            resources.getString(com.uptime.kuma.R.string.message_error_dialog)
        (view.findViewById<View>(com.uptime.kuma.R.id.imageIcon) as ImageView).setImageResource(com.uptime.kuma.R.drawable.ic_baseline_warning_24)

        button.setOnClickListener {
            builder.dismiss()
            sessionManagement.logOut() //logout
            MainActivity.instance.restartApplication()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        NETWORKLIVEDATA.removeObservers(viewLifecycleOwner)
    }


}