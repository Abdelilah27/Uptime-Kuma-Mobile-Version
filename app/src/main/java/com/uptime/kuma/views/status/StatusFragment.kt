package com.uptime.kuma.views.status

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.views.mainActivity.MainActivity.Companion.navController
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentStatusBinding
import com.uptime.kuma.models.Status
import com.uptime.kuma.views.adapters.MonitorItemAllServersAdapter
import com.uptime.kuma.views.adapters.StatusAdapter
import com.uptime.kuma.views.monitorsList.AllServersCompanionObject


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
        binding.addfloatingActionButton.setOnClickListener {
            navController.navigate(R.id.addStatusFragment)
        }

        observeStatusList()
    }

    //observe status list
    private fun observeStatusList() {
        StatusCompanionObject.statusLiveData.observe(viewLifecycleOwner, Observer {
            itemAdapter.submitList(it)
        })
    }


    override fun onItemClick(position: Int) {
        navController.navigate(
            R.id
                .groupStatusFragment
        )
        Log.d("TAG", "onItemClick: " + position)
    }

}