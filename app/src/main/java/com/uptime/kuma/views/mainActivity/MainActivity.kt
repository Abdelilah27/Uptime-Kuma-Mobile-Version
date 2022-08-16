package com.uptime.kuma.views.mainActivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tinder.scarlet.Scarlet
import com.uptime.kuma.R
import com.uptime.kuma.api.ApiUtilities
import com.uptime.kuma.api.ConnexionInterface
import com.uptime.kuma.repository.SharedRepository
import com.uptime.kuma.service.sharedData.SharedViewModel
import com.uptime.kuma.service.sharedData.SharedViewModelFactory
import com.uptime.kuma.utils.LanguageSettings
import com.uptime.kuma.views.login.LoginFragment

class MainActivity : AppCompatActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var navController: NavController
        lateinit var languageSettings: LanguageSettings
        lateinit var scarlet: Scarlet
        lateinit var webSocketService: ConnexionInterface

        //        lateinit var sharedViewModel: SharedViewModel
        lateinit var mainActivityViewModel: MainActivityViewModel
    }

    private lateinit var sharedRepository: SharedRepository

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //instance mainViewModel
        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        //instance Helper class languageSettings
        languageSettings = LanguageSettings(this)
        mainActivityViewModel.setAppLocale(this, languageSettings.language.toString())

//        setFullScreen(window)
//        lightStatusBar(window)

        LoginFragment.socketLiveData.observe(this, Observer {
            //Setup and create connexion
            scarlet = ApiUtilities.provideScarlet(it.toString())
            webSocketService = ApiUtilities.getInstance(scarlet)

            //Service Shared Data
            sharedRepository = SharedRepository(webSocketService)
            val sharedViewModel = ViewModelProvider(
                this,
                SharedViewModelFactory(sharedRepository)
            )[SharedViewModel::class.java]

            sharedViewModel.handleConnexionState(this, lifecycleScope = lifecycleScope)

        })


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}