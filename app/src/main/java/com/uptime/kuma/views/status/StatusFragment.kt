package com.uptime.kuma.views.status

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.R
import com.uptime.kuma.api.ConnexionLifecycle
import com.uptime.kuma.databinding.FragmentStatusBinding
import com.uptime.kuma.views.adapters.StatusAdapter
import com.uptime.kuma.views.mainActivity.MainActivity


class StatusFragment : Fragment(R.layout.fragment_status), StatusAdapter.OnClickLister {
    private lateinit var itemAdapter: StatusAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val binding = FragmentStatusBinding.bind(view)
        binding.apply {
            binding.statusRecycler.apply {
                itemAdapter = StatusAdapter(context, this@StatusFragment)
                adapter = itemAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        observeStatusList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MainActivity.sharedViewModel.statusLiveData.removeObservers(viewLifecycleOwner)
    }

    //observe status list
    private fun observeStatusList() {
        MainActivity.sharedViewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            itemAdapter.submitList(it)
        })
    }

    override fun onItemClick(position: Int) {

    }
}