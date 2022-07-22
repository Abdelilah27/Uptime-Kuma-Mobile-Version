package com.uptime.kuma.views.itemGroupStatus

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentGroupStatusBinding
import com.uptime.kuma.models.MonitorItem
import com.uptime.kuma.models.StatusGroup
import com.uptime.kuma.views.adapters.StatusGroupAdapter

class GroupStatusFragment : Fragment(R.layout.fragment_group_status) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val binding = FragmentGroupStatusBinding.bind(view)
        val itemAdapter = activity?.let { StatusGroupAdapter(it, getData()) }
        binding.apply {
            binding.groupStatusRecycler.apply {
                adapter = itemAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                itemAdapter?.submitList(getData())
            }
        }
    }

    private fun getData(): List<StatusGroup> {
        val subListData1 = arrayListOf<MonitorItem>()
        subListData1.add(MonitorItem(id = 1, percent = "99.2", title = "2M.ma", isOnline = false))
        subListData1.add(
            MonitorItem(
                id = 1, percent = "100", title = "Mobiblanc.ma", isOnline =
                true
            )
        )
        val subListData2 = arrayListOf<MonitorItem>()
        subListData2.add(MonitorItem(id = 1, percent = "87.29", title = "Inwi.ma", isOnline = true))
        subListData2.add(MonitorItem(id = 1, percent = "87.2", title = "Inwi.ma", isOnline = true))
        subListData2.add(
            MonitorItem(
                id = 1,
                percent = "87.29",
                title = "Inwi.ma",
                isOnline = false
            )
        )
        val data = arrayListOf<StatusGroup>()
        data.add(StatusGroup("TMA", subListData1))
        data.add(StatusGroup("Hors TMA", subListData2))
        return data
    }

}