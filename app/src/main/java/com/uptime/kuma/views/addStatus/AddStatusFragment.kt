package com.uptime.kuma.views.addStatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentAddStatusBinding


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
            findNavController().navigateUp()
        }
        return binding.root
    }
}