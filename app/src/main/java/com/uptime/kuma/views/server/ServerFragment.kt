package com.uptime.kuma.views.server

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentServerBinding
import com.uptime.kuma.views.adapters.ServerAdapter


class ServerFragment : Fragment(R.layout.fragment_server) {
    private val args: ServerFragmentArgs by navArgs()
    private lateinit var serverViewModel: ServerViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentServerBinding.bind(view)
        val serverAdapter = ServerAdapter(requireContext())
        serverViewModel = ViewModelProvider(requireActivity())[ServerViewModel::class.java]
        //get Id from args
        val serverId = args.serverId

        binding.apply {
            recyclerServerFragment.apply {
                adapter = serverAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                isNestedScrollingEnabled = false
                serverAdapter.submitList(serverViewModel.filterMonitorstatus(serverId.toInt()))
            }
        }
    }

}