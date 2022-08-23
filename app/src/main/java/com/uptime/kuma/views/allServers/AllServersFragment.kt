package com.uptime.kuma.views.allServers

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uptime.kuma.R
import com.uptime.kuma.api.ConnexionLifecycle
import com.uptime.kuma.databinding.FragmentAllServersBinding
import com.uptime.kuma.views.adapters.MonitorItemAllServersAdapter
import com.uptime.kuma.views.main.MainFragmentDirections
import com.uptime.kuma.views.mainActivity.MainActivity
import com.uptime.kuma.views.monitorsList.AllServersCompanionObject
import java.util.*

class AllServersFragment : Fragment(R.layout.fragment_all_servers),
    MonitorItemAllServersAdapter.OnClickLister {
    companion object {
        lateinit var allRecycler: RecyclerView
    }

    private lateinit var itemAdapter: MonitorItemAllServersAdapter
    private lateinit var allServersViewModel: AllServersViewModel
    private lateinit var binding: FragmentAllServersBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding = FragmentAllServersBinding.bind(view)
        ConnexionLifecycle.openConnexion()
        allRecycler = view.findViewById(R.id.all_server_recycler)
        allServersViewModel =
            ViewModelProvider(requireActivity()).get(AllServersViewModel::class.java)
        itemAdapter = activity?.let { MonitorItemAllServersAdapter(it, this, viewLifecycleOwner) }!!
        allRecycler.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            itemAdapter.setData(AllServersCompanionObject.monitorCalcul)
        }
        searchViewListener()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        AllServersCompanionObject.monitorCalculLiveData.removeObservers(viewLifecycleOwner)
        ConnexionLifecycle.closeConnexion()
    }

    //search for a monitor in monitors
    private fun searchViewListener() {
        binding.searchEditTextAllServersFragment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchText = binding.searchEditTextAllServersFragment.text.toString()
                    .toLowerCase(Locale.getDefault())
                allServersViewModel.searchMonitor(searchText)
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }


    override fun onItemClick(position: Int) {
        val serverId = allServersViewModel.tempMonitors[position].id.toString()
        val action = MainFragmentDirections.actionMainFragmentToServerFragment(serverId)
        MainActivity.navController.navigate(action)
    }


}