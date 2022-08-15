package com.uptime.kuma.views.dashbord

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentDashboardBinding
import com.uptime.kuma.views.adapters.DashboardRecyclerAdapter


class DashboardFragment : Fragment(R.layout.fragment_dashboard),
    DashboardRecyclerAdapter.OnItemClickListener {
    private lateinit var itemAdapter: DashboardRecyclerAdapter

    //    private lateinit var dashbordViewModel: DashbordViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDashboardBinding.bind(view)
        // instantiation de ViewModel
//        dashbordViewModel=ViewModelProvider(requireActivity()).get(DashbordViewModel::class.java)
        var online: Int = 0
        var offline: Int = 0
        var unknown: Int = 0
        var pause: Int = 0

        for (i in 0 until DashbordCompanionObject.newList.size) {
            Log.d(
                "TAG2",
                DashbordCompanionObject.newList[i].monitorID.toString() + "  Status  " + DashbordCompanionObject.newList[i].status
            )
            when (DashbordCompanionObject.newList[i].status) {
                0 -> offline += 1
                1 -> online += 1
                2 -> pause += 1
                else -> {
                    unknown += 1
                }
            }

        }
        binding.apply {
            dashbordRecycler.apply {
                itemAdapter = DashboardRecyclerAdapter(context, this@DashboardFragment)
                adapter = itemAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
//                dashbordRecyclerAdapter.submitList(getData())

            }
            enLigneNumber.text = online.toString()
            downNumber.text = offline.toString()
            unknownNumber.text = unknown.toString()
            pauseNumber.text = pause.toString()
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
//        Log.d(TAG, "onItemClick: ")
//        MainActivity.navController.navigate(R.id.serverFragment)

    }
}

