package com.uptime.kuma

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView =findViewById<BottomNavigationView>(R.id.BottomNavigationview)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.dashboardFragment|| destination.id == R.id.allServersFragment || destination.id == R.id.statusFragment || destination.id == R.id.parametreFragment) {

                bottomNavigationView.visibility = View.VISIBLE
            } else {

                bottomNavigationView.visibility = View.GONE
            }
        }
//        setupWithNavController(bottomNavigationView,navController)
            bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}