package com.uptime.kuma.views.dashbord

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentDashboardBinding
import com.uptime.kuma.views.adapters.DashboardRecyclerAdapter
import com.uptime.kuma.views.mainActivity.MainActivity


class DashboardFragment : Fragment(R.layout.fragment_dashboard),
    DashboardRecyclerAdapter.OnItemClickListener {
    private lateinit var itemAdapter: DashboardRecyclerAdapter

    //    private lateinit var dashbordViewModel: DashbordViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDashboardBinding.bind(view)
        // instantiation de ViewModel
//        dashbordViewModel=ViewModelProvider(requireActivity()).get(DashbordViewModel::class.java)
        binding.apply {
            dashbordRecycler.apply {
                itemAdapter = DashboardRecyclerAdapter(context, this@DashboardFragment)
                adapter = itemAdapter
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

