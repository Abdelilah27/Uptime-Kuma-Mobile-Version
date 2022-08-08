package com.uptime.kuma.views.monitorsList

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentAllServersBinding
import com.uptime.kuma.models.MonitorItem
import com.uptime.kuma.views.adapters.MonitorItemAllServersAdapter
import com.uptime.kuma.views.mainActivity.MainActivity

class AllServersFragment : Fragment(R.layout.fragment_all_servers),
    MonitorItemAllServersAdapter.OnClickLister {
    private lateinit var itemAdapter: MonitorItemAllServersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val binding = FragmentAllServersBinding.bind(view)
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
        MainActivity.sharedViewModel.monitorLiveData.observe(viewLifecycleOwner, Observer {
            itemAdapter.submitList(it)
        })
    }


    private fun getData(): List<MonitorItem> {
        val data = arrayListOf<MonitorItem>()
        data.add(MonitorItem(id = 1, percent = "99.2%", title = "2M.ma", isOnline = false))
        data.add(MonitorItem(id = 1, percent = "100%", title = "Mobiblanc.ma", isOnline = true))
        data.add(MonitorItem(id = 1, percent = "72.99%", title = "Orange.ma", isOnline = true))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = false))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = false))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = true))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = false))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = true))
        data.add(MonitorItem(id = 1, percent = "87.2%", title = "Inwi.ma", isOnline = true))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = false))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = true))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = false))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = true))
        data.add(MonitorItem(id = 1, percent = "87.2%", title = "Inwi.ma", isOnline = false))
        data.add(MonitorItem(id = 1, percent = "87.2%", title = "Inwi.ma", isOnline = false))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = true))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = false))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = true))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = false))
        return data
    }

    override fun onItemClick(position: Int) {
        MainActivity.navController.navigate(R.id.serverFragment)
//        Log.d("TAG", "onItemClick: " + position)
    }


}