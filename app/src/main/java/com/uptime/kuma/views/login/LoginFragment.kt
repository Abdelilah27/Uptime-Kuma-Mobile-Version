package com.uptime.kuma.views.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentLoginBinding


class LoginFragment : Fragment(R.layout.fragment_login) {
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
            findNavController().navigate(R.id.mainFragment)

        }
    }


}