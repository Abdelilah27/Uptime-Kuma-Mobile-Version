package com.uptime.kuma.views.bienvenue

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentBienvenueBinding
import com.uptime.kuma.views.mainActivity.MainActivity


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
            onCommencerFinished()
            val action =
                BienvenueFragmentDirections.actionBienvenueFragmentToLoginFragment()
            findNavController().navigate(action)

        }

        return binding.root
    }

    private fun onCommencerFinished() {
        val sharedpref = requireActivity().getSharedPreferences("Bienvenue", Context.MODE_PRIVATE)
        val editor = sharedpref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}