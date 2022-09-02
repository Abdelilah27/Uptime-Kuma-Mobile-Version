package com.uptime.kuma.views.login

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentLoginBinding
import com.uptime.kuma.utils.AUTOLOGIN
import com.uptime.kuma.utils.NETWORKLIVEDATA
import com.uptime.kuma.utils.SessionManagement
import com.uptime.kuma.views.mainActivity.MainActivity
import kotlinx.coroutines.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.coroutines.CoroutineContext


class LoginFragment : Fragment(R.layout.fragment_login), CoroutineScope {
    lateinit var binding: FragmentLoginBinding
    lateinit var sessionManagement: SessionManagement
    lateinit var progressDialog: ProgressDialog
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
        progressDialog = ProgressDialog(requireContext())
        sessionManagement = SessionManagement(requireContext())
        binding.buttonLogin.setOnClickListener {
            if (binding.socketUrl.text.isNotEmpty()) {
                val formatSocketLink = formatSocketLink(binding.socketUrl.text.toString())
                if (verificationSocketLink(formatSocketLink)) {
                    (activity as MainActivity).setUpConnexion(formatSocketLink)
                    //show progress dialog
                    progressDialog.show()
                    progressDialog.setContentView(R.layout.progress_dialog)
                    progressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

                    launch {
                        delay(7000)
                        withContext(Dispatchers.Main) {
                            if (AUTOLOGIN == 1) {
                                sessionManagement.creatLoginSocket(formatSocketLink)
                                progressDialog.dismiss()
                                findNavController().navigate(R.id.mainFragment)
                            } else {
                                val action =
                                    LoginFragmentDirections.actionLoginFragmentToLoginPlusFragment(
                                        formatSocketLink
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    }


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

    private fun formatSocketLink(link: String): String {
        return "ws://$link/socket.io/?EIO=4&transport=websocket"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        NETWORKLIVEDATA.removeObservers(viewLifecycleOwner)
        progressDialog.dismiss()
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

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

}