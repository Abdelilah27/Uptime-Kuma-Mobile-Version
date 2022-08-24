package com.uptime.kuma.views.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.uptime.kuma.R
import com.uptime.kuma.api.ConnexionLifecycle
import com.uptime.kuma.databinding.FragmentLoginBinding
import com.uptime.kuma.utils.NETWORKSTATUS
import com.uptime.kuma.utils.SessionManagement
import com.uptime.kuma.views.mainActivity.MainActivity
import java.util.regex.Matcher
import java.util.regex.Pattern


class LoginFragment : Fragment(R.layout.fragment_login) {
    lateinit var binding: FragmentLoginBinding
    lateinit var sessionManagement: SessionManagement


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManagement = SessionManagement(requireContext())
        //redirection to the dashboard fragment
        if (sessionManagement.checkIsLogged()) {
            (activity as MainActivity).setUpConnexion(sessionManagement.getSocket())
            findNavController().navigate(R.id.mainFragment)
        }

        binding.buttonLogin.setOnClickListener {
            if (binding.socketUrl.text.isNotEmpty()) {
                if (verificationSocketLink(binding.socketUrl.text.toString())) {
                    (activity as MainActivity).setUpConnexion(binding.socketUrl.text.toString())
                    binding.progressBar.visibility = View.VISIBLE
                    MainActivity.sharedViewModel.networkLiveData.observe(
                        viewLifecycleOwner,
                        Observer {
                            when (it) {
                                "1" -> {
                                    sessionManagement.creatLoginSocket(binding.socketUrl.text.toString())
                                    binding.progressBar.visibility = View.GONE
                                    findNavController().navigate(R.id.mainFragment)

                                }
                                "2", "3", "6" -> {
                                    binding.progressBar.visibility = View.GONE
                                    showErrorDialog()
                                }
                            }
                        })

                } else {
                    Toast.makeText(
                        context,
                        resources.getString(com.uptime.kuma.R.string.valide_socket_url),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    context,
                    resources.getString(com.uptime.kuma.R.string.empty_socket_url),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
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

        button.setOnClickListener {
            builder.dismiss()
            NETWORKSTATUS = "0" //set connexion to open
            ConnexionLifecycle.closeConnexion()
            binding.socketUrl.text.clear()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    //socket link regular expression
    private fun verificationSocketLink(socketLink: String): Boolean {
        val regex =
            "^(wss|ws|http|https):(\\/\\/)([a-zA-Z]+||[0-9]*).(.*)\$"
        //Compile regular expression to get the pattern
        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(socketLink)
        return matcher.matches()
    }

}