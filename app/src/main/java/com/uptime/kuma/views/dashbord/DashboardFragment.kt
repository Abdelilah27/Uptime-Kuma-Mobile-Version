package com.uptime.kuma.views.dashbord

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentDashboardBinding
import com.uptime.kuma.models.dashboardCalcul.CalculDashboardItem
import com.uptime.kuma.utils.CALCUL
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
    private lateinit var shimmerView: ShimmerFrameLayout
    private lateinit var shimmerViewCalcul: ShimmerFrameLayout

    //    private lateinit var dashbordViewModel: DashbordViewModelf
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)
        // instantiation de ViewModel
        dashbordViewModel = ViewModelProvider(requireActivity()).get(DashbordViewModel::class.java)
        shimmerView = binding.dashboardShimmer
        shimmerViewCalcul = binding.dashboardShimmerCalcul
        shimmerView.startShimmerAnimation()
        shimmerViewCalcul.startShimmerAnimation()
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
            getStatistics()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MainActivity.sharedViewModel.monitorStatusLiveData.removeObservers(viewLifecycleOwner)
        MainActivity.sharedViewModel.newLiveData.removeObservers(viewLifecycleOwner)
//        ConnexionLifecycle.closeConnexion()
    }


    //observe monitorstatus list
    private fun observeMonitorsList() {
        MainActivity.sharedViewModel.monitorStatusLiveData.observe(
            viewLifecycleOwner,
            Observer { data ->
                itemAdapter.setData(data ?: listOf())
                if (data.size > 0) {
                    shimmerView.stopShimmerAnimation()
                    shimmerView.visibility = View.GONE
                    binding.dashbordRecycler.visibility = View.VISIBLE
                }
            })
    }

    override fun onItemClick(position: String) {
        val action = MainFragmentDirections.actionMainFragmentToServerFragment(position)
        MainActivity.navController.navigate(action)
    }

    private fun getStatistics() {
        MainActivity.sharedViewModel.newLiveData.observe(
            viewLifecycleOwner, Observer { data ->
                var enLigne = 0
                var horsLigne = 0
                var enPause = 0
                var unKnown = 0
                data.forEach {
                    when (it.status) {
                        0 -> {
                            horsLigne++
                        }
                        1 -> {
                            enLigne++
                        }
                        2 -> {
                            enPause++
                        }
                        else -> {
                            unKnown++
                        }
                    }
                }

//                Log.d("horsLigne", horsLigne.toString())
//                Log.d("enLigne", enLigne.toString())
//                Log.d("enPause", enPause.toString())
                CALCUL = ArrayList()
                CALCUL?.add(
                    CalculDashboardItem(
                        resources.getString(R.string.online), enLigne
                            .toString()
                    )
                )
                CALCUL?.add(
                    CalculDashboardItem(
                        resources.getString(R.string.offline),
                        horsLigne.toString()
                    )
                )

                CALCUL?.add(
                    CalculDashboardItem(
                        resources.getString(R.string.pause), enPause
                            .toString()
                    )
                )
                CALCUL?.add(
                    CalculDashboardItem(
                        resources.getString(R.string.unknown), unKnown
                            .toString()
                    )
                )

                shimmerViewCalcul.stopShimmerAnimation()
                shimmerViewCalcul.visibility = View.GONE
                binding.calculRecycler.visibility = View.VISIBLE
                calculItemAdapter.setData(CALCUL ?: listOf())
            })
    }

}