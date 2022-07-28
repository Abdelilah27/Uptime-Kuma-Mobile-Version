package com.uptime.kuma

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.uptime.kuma.databinding.FragmentMainBinding
import com.uptime.kuma.views.dashbord.DashboardFragment
import com.uptime.kuma.views.monitorsList.AllServersFragment
import com.uptime.kuma.views.parametre.ParametreFragment
import com.uptime.kuma.views.splash.SplashFragment
import com.uptime.kuma.views.status.StatusFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val dashboardFragment = DashboardFragment()
    private val allServersdFragment = AllServersFragment()
    private val statusFragment = StatusFragment()
    private val parametreFragment = ParametreFragment()
    private val splashFragment = SplashFragment()
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
        replaceFragment(dashboardFragment)
        binding.BottomNavigationView.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.dashboardFragment -> replaceFragment(dashboardFragment)
                R.id.allServersFragment -> replaceFragment(allServersdFragment)
                R.id.statusFragment -> replaceFragment(statusFragment)
                R.id.parametreFragment -> replaceFragment(parametreFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = (activity as FragmentActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_frame_layout, fragment)
        transaction.commit()

    }
}