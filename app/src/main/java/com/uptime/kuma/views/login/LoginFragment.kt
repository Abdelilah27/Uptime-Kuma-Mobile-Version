package com.uptime.kuma.views.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.uptime.kuma.api.NetworkResult
import com.uptime.kuma.databinding.FragmentLoginBinding


class LoginFragment : Fragment(com.uptime.kuma.R.layout.fragment_login) {
    companion object {
        private val _socketLiveData = MutableLiveData<String>()
        val socketLiveData: LiveData<String>
            get() = _socketLiveData
    }

    lateinit var binding: FragmentLoginBinding

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

        binding.buttonLogin.setOnClickListener {
            _socketLiveData.postValue(binding.socketUrl.text.toString())
            binding.progressBar.visibility = View.VISIBLE
            NetworkResult().set(MutableLiveData("0"))//set connexion to open
            NetworkResult.instance.get().observe(viewLifecycleOwner, Observer {
                when (NetworkResult.instance.get().value) {
                    "1" -> {
                        binding.progressBar.visibility = View.GONE
                        findNavController().navigate(com.uptime.kuma.R.id.mainFragment)
                    }
                    "2" -> {
                        Log.d("ccc", "onViewCreated: ")
                        binding.progressBar.visibility = View.GONE
                        showErrorDialog()
                    }
                    "3" -> {
                        binding.progressBar.visibility = View.GONE
                        showErrorDialog()
                    }

                }
            }
            )

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
            NetworkResult().set(MutableLiveData("0"))//set connexion to open
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }


}