package com.uptime.kuma.views.dashbord

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentDashboardBinding
import com.uptime.kuma.models.dashboardCalcul.CalculDashboardItem
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem
import com.uptime.kuma.utils.CALCUL
import com.uptime.kuma.utils.RecyclerClickInterface
import com.uptime.kuma.utils.UpdateData
import com.uptime.kuma.views.adapters.DashboardRecyclerAdapter
import com.uptime.kuma.views.adapters.DashboardRecyclerCalculItemAdapter
import com.uptime.kuma.views.main.MainFragmentDirections
import com.uptime.kuma.views.mainActivity.MainActivity


class DashboardFragment : Fragment(R.layout.fragment_dashboard),
    RecyclerClickInterface, UpdateData {
    companion object {
        lateinit var progressDialog: ProgressDialog
        lateinit var instance: DashboardFragment
    }

    private val TAG = "DashboardFragment"
    private lateinit var itemAdapter: DashboardRecyclerAdapter
    private lateinit var calculItemAdapter: DashboardRecyclerCalculItemAdapter
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var shimmerView: ShimmerFrameLayout
    private lateinit var shimmerViewCalcul: ShimmerFrameLayout
    private var executeOnce = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instance = this
        binding = FragmentDashboardBinding.bind(view)
        shimmerView = binding.dashboardShimmer
        shimmerViewCalcul = binding.dashboardShimmerCalcul
        //to show shimmer effect once
        if (!executeOnce) {
            shimmerView.startShimmerAnimation()
            shimmerViewCalcul.startShimmerAnimation()
            executeOnce = true
        } else {
            shimmerView.stopShimmerAnimation()
            shimmerView.visibility = View.GONE
            binding.dashbordRecycler.visibility = View.VISIBLE

            shimmerViewCalcul.stopShimmerAnimation()
            shimmerViewCalcul.visibility = View.GONE
            binding.calculRecycler.visibility = View.VISIBLE
        }
        //init progress dialog
        progressDialog = ProgressDialog(requireContext())

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
    }


    //observe monitorstatus list
    private fun observeMonitorsList() {
        MainActivity.sharedViewModel.monitorStatusLiveData.observe(
            viewLifecycleOwner,
            Observer { data ->
                itemAdapter.setData(data ?: listOf())
                //show shimmer effect for 1s in case data are retrieved
                Handler().postDelayed({
                    if (data.size > 0) {
                        shimmerView.stopShimmerAnimation()
                        shimmerView.visibility = View.GONE
                        binding.dashbordRecycler.visibility = View.VISIBLE
                    }
                }, 1000)


            })
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

                //show shimmer effect for 1s in case data are retrieved
                Handler().postDelayed({
                    shimmerViewCalcul.stopShimmerAnimation()
                    shimmerViewCalcul.visibility = View.GONE
                    binding.calculRecycler.visibility = View.VISIBLE
                }, 1000)


                calculItemAdapter.setData(CALCUL ?: listOf())
            })
    }

    override fun onItemClick(position: Int) {
        progressDialog.show()
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val args = position.toString() + TAG
        val action = MainFragmentDirections.actionMainFragmentToServerFragment(args)
        MainActivity.navController.navigate(action)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceivedData(data: MonitorStatusItem) {
        var status = resources.getString(com.uptime.kuma.R.string.downStatus)
        when (data.status) {
            1 -> status = resources.getString(com.uptime.kuma.R.string.upStatus)
            0 -> status = resources.getString(com.uptime.kuma.R.string.downStatus)
        }
        MainActivity.instance.sendNotification(
            (data.name + " - " + status + " - " + data.msg) ?: ""
        )
        Log.d("0000", "onReceivedData: ++++++")
    }

}