package com.uptime.kuma.views.dashbord

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.MainActivity
import com.uptime.kuma.MainFragment
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentDashboardBinding
import com.uptime.kuma.models.DashbordItems
import com.uptime.kuma.views.adapters.DashboardRecyclerAdapter


class DashboardFragment : Fragment(R.layout.fragment_dashboard),
    DashboardRecyclerAdapter.OnItemClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDashboardBinding.bind(view)

        val dashbordRecyclerAdapter = DashboardRecyclerAdapter(requireContext(), this)
        binding.apply {
            dashbordRecycler.apply {
                adapter = dashbordRecyclerAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                dashbordRecyclerAdapter.submitList(getData())
            }
        }
    }

    override fun onItemClick(position: Int) {
        MainFragment.navMainController.navigate(R.id.action_dashboardFragment_to_serverFragment)
    }

    private fun getData(): List<DashbordItems> {
        val data = arrayListOf<DashbordItems>()
        data.add(
            DashbordItems(

                "Inwi",
                true,
                "2022-07-20 02:07:49",
                "connect EHOSTUNREACH 41.214.190.232:9090aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            )
        )
        data.add(
            DashbordItems(

                "Orange",
                false,
                "2022-07-20 02:07:49",
                "connect EHOSTUNREACH 41.214.190.232:9090"
            )
        )
        data.add(
            DashbordItems(

                "Wana",
                true,
                "2022-07-20 02:07:49",
                "connect EHOSTUNREACH 41.214.190.232:9090"
            )
        )
        data.add(
            DashbordItems(

                "Miditel",
                true,
                "2022-07-20 02:07:49",
                "connect EHOSTUNREACH 41.214.190.232:9090"
            )
        )
        data.add(
            DashbordItems(

                "MarocTelecom",
                false,
                "2022-07-20 02:07:49",
                "connect EHOSTUNREACH 41.214.190.232:9090"
            )
        )
        data.add(
            DashbordItems(

                "2M",
                false,
                "2022-07-20 02:07:49",
                "connect EHOSTUNREACH 41.214.190.232:9090"
            )
        )
        data.add(
            DashbordItems(

                "Mobiblanc",
                true,
                "2022-07-20 02:07:49",
                "connect EHOSTUNREACH 41.214.190.232:9090"
            )
        )
        data.add(
            DashbordItems(

                "MobiblancVPN",
                false,
                "2022-07-20 02:07:49",
                "connect EHOSTUNREACH 41.214.190.232:9090"
            )
        )
        data.add(
            DashbordItems(

                "Test404",
                false,
                "2022-07-20 02:07:49",
                "connect EHOSTUNREACH 41.214.190.232:9090"
            )
        )
        data.add(
            DashbordItems(

                "ValarMorghulis",
                true,
                "2022-07-20 02:07:49",
                "connect EHOSTUNREACH 41.214.190.232:9090"
            )
        )
        return data
    }
}