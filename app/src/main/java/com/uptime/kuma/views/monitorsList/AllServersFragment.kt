package com.uptime.kuma.views.monitorsList

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.R
import com.uptime.kuma.adapters.MonitorItemAllServersAdapter
import com.uptime.kuma.databinding.FragmentAllServersBinding
import com.uptime.kuma.models.MonitorItem

class AllServersFragment : Fragment(R.layout.fragment_all_servers),
    MonitorItemAllServersAdapter.OnClickLister {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val binding = FragmentAllServersBinding.bind(view)
        val itemAdapter = activity?.let { MonitorItemAllServersAdapter(it, this) }
        binding.apply {
            binding.allServerRecycler.apply {
                adapter = itemAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                itemAdapter?.submitList(getData())

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_servers, menu)
        val searchItem = menu.findItem(R.id.search_action)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true

            }

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
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = true))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = false))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = true))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = false))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = true))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = false))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = false))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = true))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = false))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = true))
        data.add(MonitorItem(id = 1, percent = "87.29%", title = "Inwi.ma", isOnline = false))
        return data
    }

    override fun onItemClick(position: Int) {
        findNavController().navigate(R.id.action_allServersFragment_to_serverFragment)
//        Log.d("TAG", "onItemClick: " + position)
    }


}