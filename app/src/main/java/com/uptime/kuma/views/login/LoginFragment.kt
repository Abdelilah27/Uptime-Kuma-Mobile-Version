package com.uptime.kuma.views.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var Email = ""
    private var Password = ""

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.getSupportActionBar()!!.hide()
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogin.setOnClickListener {
            if (binding.emailEdt.text.toString() != Email || binding.passwordEdt.text.toString() != Password) {
//                binding.passwordEdt.setError("Invalid")
//                Toast.makeText(activity,"Error",Toast.LENGTH_LONG).show()
                Snackbar.make(binding.root, "Email et/ou Mot de passe incorrect(s)", Snackbar.LENGTH_LONG).show()
            } else
                Navigation.findNavController(view)
                    .navigate(R.id.action_loginFragment_to_dashboardFragment)
        }
    }


}