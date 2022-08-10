package com.uptime.kuma.views.allServers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentAllServersBinding
import com.uptime.kuma.views.adapters.MonitorItemAllServersAdapter
import com.uptime.kuma.views.mainActivity.MainActivity

class AllServersFragment : Fragment(R.layout.fragment_all_servers),
    MonitorItemAllServersAdapter.OnClickLister {
    private lateinit var itemAdapter: MonitorItemAllServersAdapter
    private lateinit var allServersViewModel: AllServersViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val binding = FragmentAllServersBinding.bind(view)

        allServersViewModel =
            ViewModelProvider(requireActivity()).get(AllServersViewModel::class.java)

        binding.allServerRecycler.apply {
            itemAdapter = MonitorItemAllServersAdapter(context, this@AllServersFragment)
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        observeMonitorsList()
    }

    //observe monitor list
    private fun observeMonitorsList() {
        AllServersCompanionObject.monitorLiveData.observe(viewLifecycleOwner, Observer {
            itemAdapter.submitList(it)
        })
    }

    override fun onItemClick(position: Int) {
        MainActivity.navController.navigate(R.id.serverFragment)
//        Log.d("TAG", "onItemClick: " + position)
    }


}