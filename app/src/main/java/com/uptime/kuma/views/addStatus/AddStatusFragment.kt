package com.uptime.kuma.views.addStatus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.uptime.kuma.MainActivity
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentAddStatusBinding
import com.uptime.kuma.views.main.MainFragment


class AddStatusFragment : Fragment(R.layout.fragment_add_status) {

    private var _binding: FragmentAddStatusBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddStatusBinding.inflate(inflater, container, false)
        binding.addNewStatusButton.setOnClickListener {
            //TODO
            Log.d("A", "onCreateView: " + findNavController().currentDestination)
            Log.d("B", "onCreateView: " + MainActivity.navController.currentDestination)
            Log.d(
                "C", "onCreateView: " + requireView().findNavController(
                ).currentDestination
            )
            Log.d(
                "D", "onCreateView: " + MainFragment.navController.currentDestination
            )
//            findNavController().navigateUp()
//            MainFragment.navController.navigate(R.id.allServersFragment)
        }
        return binding.root
    }
}