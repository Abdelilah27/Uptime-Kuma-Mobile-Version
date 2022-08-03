package com.uptime.kuma.views.bienvenue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uptime.kuma.MainActivity
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentBienvenueBinding


class BienvenueFragment : Fragment(R.layout.fragment_bienvenue) {

    private var _binding: FragmentBienvenueBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        (activity as AppCompatActivity?)!!.getSupportActionBar()!!.hide()
//        activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        _binding = FragmentBienvenueBinding.inflate(inflater, container, false)

        binding.buttonCommencerBiennvenueFragment.setOnClickListener {
            MainActivity.navController.navigate(R.id.loginFragment)
        }

        return binding.root
    }


}