package com.uptime.kuma

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.uptime.kuma.databinding.FragmentMainBinding
import com.uptime.kuma.views.dashbord.DashboardFragment
import com.uptime.kuma.views.monitorsList.AllServersFragment
import com.uptime.kuma.views.parametre.ParametreFragment
import com.uptime.kuma.views.status.StatusFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    companion object {
        lateinit var navMainController: NavController
    }

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

        val appCompat = requireActivity() as AppCompatActivity

        val navHostFragment =
            (activity as MainFragment).childFragmentManager.findFragmentById(R.id
                .fragmentContainerView) as
                    NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.bottomNavigationView.setupWithNavController(navController)
    }
}