package com.uptime.kuma.views.loginPlus

import android.app.ProgressDialog
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
import androidx.navigation.fragment.navArgs
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentLoginPlusBinding
import com.uptime.kuma.utils.NETWORKLIVEDATA
import com.uptime.kuma.utils.SessionManagement
import com.uptime.kuma.views.mainActivity.MainActivity

class LoginPlusFragment : Fragment(R.layout.fragment_login_plus) {
    lateinit var binding: FragmentLoginPlusBinding
    lateinit var sessionManagement: SessionManagement
    lateinit var progressDialog: ProgressDialog
    private val argsSocket: LoginPlusFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginPlusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(requireContext())
        progressDialog.dismiss()
        sessionManagement = SessionManagement(requireContext())
        val socketUrl = argsSocket.socketUrl
        binding.apply {
            buttonLoginPlus.setOnClickListener {
                if (mailLoginP.text.isNotEmpty() && passLoginP.text.isNotEmpty()) {
                    //show progress dialog
                    progressDialog.show()
                    progressDialog.setContentView(R.layout.progress_dialog)
                    progressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                    MainActivity.sharedViewModel.sendQuery(
                        MainActivity.sharedViewModel.sendLogin(
                            mailLoginP.text.toString(),
                            passLoginP.text.toString()
                        )
                    )
                    NETWORKLIVEDATA.observe(viewLifecycleOwner, Observer {
                        when (it) {
                            "8" -> {
                                //store in share preferences
                                sessionManagement.creatLoginSocket(
                                    socketUrl, mailLoginP.text
                                        .toString(), passLoginP.text.toString()
                                )
                                progressDialog.dismiss()
                                findNavController().navigate(R.id.mainFragment)
                            }
                            "2", "3", "6", "9" -> {
                                progressDialog.dismiss()
                                showErrorDialog()
                            }
                        }
                    })
                } else {
                    Toast.makeText(
                        context,
                        resources.getString(com.uptime.kuma.R.string.empty_socket_url),
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
            binding.mailLoginP.text.clear()
            binding.passLoginP.text.clear()
            MainActivity.instance.restartApplication()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        NETWORKLIVEDATA.removeObservers(viewLifecycleOwner)
        progressDialog.dismiss()
    }


}