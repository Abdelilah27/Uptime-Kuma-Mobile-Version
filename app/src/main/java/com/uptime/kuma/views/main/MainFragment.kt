package com.uptime.kuma.views.main

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentMainBinding
import com.uptime.kuma.utils.NETWORKLIVEDATA
import com.uptime.kuma.utils.RestartApp
import com.uptime.kuma.utils.SessionManagement

class MainFragment : Fragment(R.layout.fragment_main), RestartApp {
    companion object {
        lateinit var navController: NavController
    }

    lateinit var binding: FragmentMainBinding
    lateinit var sessionManagement: SessionManagement

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val navHost = childFragmentManager.findFragmentById(R.id.nav_host_frame_layout) as
                NavHostFragment
        navController = navHost.findNavController()

        view.findViewById<BottomNavigationView>(R.id.BottomNavigationView)
            .setupWithNavController(navController)
        NETWORKLIVEDATA.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    "2", "3", "6" -> {
                        showErrorDialog()
                    }
                }
            })


    }

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
        sessionManagement = SessionManagement(requireContext())
        button.setOnClickListener {
            builder.dismiss()
            sessionManagement.logOut()
            restartApplication()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    override fun restartApplication() {
        val intent = requireActivity().baseContext.packageManager.getLaunchIntentForPackage(
            requireActivity().baseContext.packageName
        )
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        Process.killProcess(Process.myPid())
        System.exit(0)
    }

}