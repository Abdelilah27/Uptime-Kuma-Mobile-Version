package com.uptime.kuma.views.status

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentStatusBinding
import com.uptime.kuma.models.Status
import com.uptime.kuma.views.adapters.StatusAdapter


class StatusFragment : Fragment(R.layout.fragment_status), StatusAdapter.OnClickLister {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val binding = FragmentStatusBinding.bind(view)
        val itemAdapter = activity?.let { StatusAdapter(it, this) }
        binding.apply {
            binding.statusRecycler.apply {
                adapter = itemAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                itemAdapter?.submitList(getData())
            }
        }
        binding.addfloatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_statusFragment_to_addStatusFragment)
//            MainFragment.navMainController.navigate(
//                R.id
//                    .action_statusFragment_to_addStatusFragment
//            )
//            Log.d("TAG", "onViewCreated: "+ MainActivity.navController.currentDestination)
//            MainActivity.navController.navigate(R.id.addStatusFragment)
//            Log.d("TAG", "onViewCreated: " + navController.currentDestination)
//            val action = StatusFragmentDirections
//                .actionStatusFragmentToAddStatusFragment()
//            MainActivity.navController.navigate(action)

//            Navigation.findNavController(view).navigate(
//                StatusFragmentDirections
//                    .actionStatusFragmentToAddStatusFragment()
//            )
//            if (findNavController().currentDestination?.id == R.id.addStatusFragment)
//                findNavController().navigate(
//                    StatusFragmentDirections
//                        .actionStatusFragmentToAddStatusFragment()
//                )

//            Log.d("scatC", "onCreate:" + NavControllerHelper.instance.get())
//            NavControllerHelper.instance.get().navigate(
//                R.id
//                    .action_statusFragment_to_addStatusFragment
//            )
        }
    }

    private fun getData(): List<Status> {
        val data = arrayListOf<Status>()
        data.add(Status(R.drawable.ic_icon, "Production", "/status/production/"))
        data.add(Status(R.drawable.ic_icon, "Production", "/status/production/"))
        data.add(Status(R.drawable.ic_icon, "Production", "/status/production/"))
        data.add(Status(R.drawable.ic_icon, "Production", "/status/production/"))
        data.add(Status(R.drawable.ic_icon, "Dev", "/status/dev/"))
        data.add(Status(R.drawable.ic_icon, "Dev", "/status/dev/"))
        data.add(Status(R.drawable.ic_icon, "Dev", "/status/dev/"))
        data.add(Status(R.drawable.ic_icon, "Moov Ci Dev", "/status/Moovcidev/"))
        data.add(Status(R.drawable.ic_icon, "Moov Ci Dev", "/status/Moovcidev/"))
        data.add(Status(R.drawable.ic_icon, "Moov Ci Dev", "/status/Moovcidev/"))
        data.add(Status(R.drawable.ic_icon, "Moov Ci Dev", "/status/Moovcidev/"))
        data.add(Status(R.drawable.ic_icon, "Moov Ci Dev", "/status/Moovcidev/"))
        data.add(Status(R.drawable.ic_icon, "Production", "/status/production/"))
        data.add(Status(R.drawable.ic_icon, "Production", "/status/production/"))
        data.add(Status(R.drawable.ic_icon, "Production", "/status/production/"))
        data.add(Status(R.drawable.ic_icon, "Production", "/status/production/"))
        return data
    }

    override fun onItemClick(position: Int) {
        findNavController().navigate(
            R.id
                .action_statusFragment_to_groupStatusFragment
        )
        Log.d("TAG", "onItemClick: " + position)
    }

}