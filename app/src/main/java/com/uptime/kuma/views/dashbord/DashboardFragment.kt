package com.uptime.kuma.views.dashbord

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.views.mainActivity.MainActivity
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentDashboardBinding
import com.uptime.kuma.models.DashbordItems
import com.uptime.kuma.views.adapters.DashboardRecyclerAdapter
import com.uptime.kuma.views.allServers.AllServersCompanionObject


class DashboardFragment : Fragment(R.layout.fragment_dashboard),
    DashboardRecyclerAdapter.OnItemClickListener {
//    private lateinit var dashbordViewModel: DashbordViewModel
    private lateinit var itemAdapter: DashboardRecyclerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDashboardBinding.bind(view)
        // instantiation de ViewModel
//        dashbordViewModel=ViewModelProvider(requireActivity()).get(DashbordViewModel::class.java)
        val dashbordRecyclerAdapter = DashboardRecyclerAdapter(requireContext(), this)
        binding.apply {
            dashbordRecycler.apply {
                adapter = dashbordRecyclerAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
//                dashbordRecyclerAdapter.submitList(getData())
            }
            observeMonitorsList()
        }
    }

    //observe monitorstatus list
    private fun observeMonitorsList() {
        DashbordCompanionObject.monitorStatusLiveData.observe(viewLifecycleOwner, Observer {
            itemAdapter.submitList(it)
        })
    }

    override fun onItemClick(position: Int) {
        MainActivity.navController.navigate(R.id.serverFragment)
    }
}

