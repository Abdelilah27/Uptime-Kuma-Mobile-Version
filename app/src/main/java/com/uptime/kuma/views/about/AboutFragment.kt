package com.uptime.kuma.views.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentAboutBinding
import com.uptime.kuma.databinding.FragmentApparenceReglageBinding
import com.uptime.kuma.views.mainActivity.MainActivity

class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.aboutback.setOnClickListener {
            MainActivity.navController.navigateUp()
        }
        // Inflate the layout for this fragment
        return root
    }

}