package com.uptime.kuma.views.dashbord

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentDashboardBinding
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem
import com.uptime.kuma.utils.STATUS
import com.uptime.kuma.views.adapters.DashboardRecyclerAdapter
import com.uptime.kuma.views.dashbord.utils.UpdateData
import com.uptime.kuma.views.main.MainFragmentDirections
import com.uptime.kuma.views.mainActivity.MainActivity
import kotlinx.coroutines.flow.combine


class DashboardFragment : Fragment(R.layout.fragment_dashboard),UpdateData,
    DashboardRecyclerAdapter.OnItemClickListener {
    private lateinit var itemAdapter: DashboardRecyclerAdapter
    private lateinit var dashbordViewModel: DashbordViewModel
    private lateinit var binding: FragmentDashboardBinding
    companion object {
        lateinit var  instance : DashboardFragment
    }

    //    private lateinit var dashbordViewModel: DashbordViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        instance=this
        super.onViewCreated(view, savedInstanceState)
         binding = FragmentDashboardBinding.bind(view)
        // instantiation de ViewModel
        dashbordViewModel = ViewModelProvider(requireActivity()).get(DashbordViewModel::class.java)
        binding.apply {
            dashbordRecycler.apply {
                itemAdapter = DashboardRecyclerAdapter(context, this@DashboardFragment)
                adapter = itemAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            enLigneNumber.text = dashbordViewModel.online.toString()
            downNumber.text = dashbordViewModel.offline.toString()
            unknownNumber.text = dashbordViewModel.unknown.toString()
            pauseNumber.text = dashbordViewModel.pause.toString()
            observeMonitorsList()
        }

        //get statistics
        dashbordViewModel.calculStatistics()
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
}

