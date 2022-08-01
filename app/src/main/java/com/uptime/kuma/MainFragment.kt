package com.uptime.kuma

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uptime.kuma.databinding.FragmentMainBinding
import com.uptime.kuma.views.dashbord.DashboardFragment
import com.uptime.kuma.views.monitorsList.AllServersFragment
import com.uptime.kuma.views.parametre.ParametreFragment
import com.uptime.kuma.views.status.StatusFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val dashboardFragment = DashboardFragment()
    private val allServersdFragment = AllServersFragment()
    private val statusFragment = StatusFragment()
    private val parametreFragment = ParametreFragment()
    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
//        val appCompat = requireActivity() as AppCompatActivity
//        val navHostFragment =
//            appCompat.supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as
//                    NavHostFragment
//        navMainController = navHostFragment.navController
//        binding.bottomNavigationView.setupWithNavController(navMainController)

//        val appCompat = requireActivity() as AppCompatActivity
//
//        val navHostFragment =
//            activity!!.supportFragmentManager.findFragmentById(R.id
//                .fragmentContainerView) as
//                    NavHostFragment
//        val navController = navHostFragment.findNavController()
//
//        binding.bottomNavigationView.setupWithNavController(navController)

//        val navHostFragment =
//            (activity!!.supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as
//                    NavHostFragment)
//        val inflater = navHostFragment.navController.navInflater
//        val graph = inflater.inflate(R.navigation.main_fragment__nav_graph)
////graph.addArgument("argument", NavArgument)
//        graph.setStartDestination(R.id.dashboardFragment)
////or
////graph.setStartDestination(R.id.fragment2)
//
//        navHostFragment.navController.graph = graph


        val navHost = childFragmentManager.findFragmentById(R.id.fragmentContainerView) as
                NavHostFragment
        val navController = navHost.findNavController()

        view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setupWithNavController(navController)


    }
}