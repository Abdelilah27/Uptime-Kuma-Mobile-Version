package com.uptime.kuma.views.dashbord

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentDashboardBinding
import com.uptime.kuma.utils.STATUS
import com.uptime.kuma.views.adapters.DashboardRecyclerAdapter
import com.uptime.kuma.views.adapters.DashboardRecyclerCalculItemAdapter
import com.uptime.kuma.views.main.MainFragmentDirections
import com.uptime.kuma.views.mainActivity.MainActivity


class DashboardFragment : Fragment(R.layout.fragment_dashboard),
    DashboardRecyclerAdapter.OnItemClickListener {
    private lateinit var itemAdapter: DashboardRecyclerAdapter
    private lateinit var dashbordViewModel: DashbordViewModel
    private lateinit var calculItemAdapter: DashboardRecyclerCalculItemAdapter
    private lateinit var binding: FragmentDashboardBinding
    companion object {
        lateinit var  instance : DashboardFragment
    }

    //    private lateinit var dashbordViewModel: DashbordViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDashboardBinding.bind(view)
        // instantiation de ViewModel
        dashbordViewModel = ViewModelProvider(requireActivity()).get(DashbordViewModel::class.java)
        binding.apply {
            calculRecycler.apply {
                calculItemAdapter = DashboardRecyclerCalculItemAdapter(context)
                adapter = calculItemAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }

            dashbordRecycler.apply {
                itemAdapter = DashboardRecyclerAdapter(context, this@DashboardFragment)
                adapter = itemAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
           observeMonitorsList()
            getStatisctics()
        }
    }
    private fun getStatisctics() {
        dashbordViewModel.calculItemLiveData.observe(viewLifecycleOwner, Observer { data ->
            calculItemAdapter.setData(data)
        })
    }

    //observe monitorstatus list
    private fun observeMonitorsList() {
        DashbordCompanionObject.monitorStatusLiveData.observe(viewLifecycleOwner, Observer { data ->
            STATUS = data
            itemAdapter.setData(STATUS?: listOf())


        })
    }

    override fun onItemClick(position: String) {
        val action = MainFragmentDirections.actionMainFragmentToServerFragment(position)
        MainActivity.navController.navigate(action)
    }

    override fun onReceivedData(data: MonitorStatusItem) {
        Log.d("element", "onReceivedData: ")
        STATUS?.add(data)
        itemAdapter.setData(STATUS?: listOf())
        binding.dashbordRecycler.adapter = itemAdapter


    }

