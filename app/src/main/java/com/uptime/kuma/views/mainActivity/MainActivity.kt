package com.uptime.kuma.views.mainActivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
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
import com.uptime.kuma.utils.SaveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var navController: NavController
        lateinit var languageSettings: LanguageSettings
        lateinit var scarlet: Scarlet
        lateinit var webSocketService: ConnexionInterface
        lateinit var mainActivityViewModel: MainActivityViewModel
        lateinit var saveData: SaveData
        lateinit var sharedViewModel: SharedViewModel
    }

    private lateinit var sharedRepository: SharedRepository


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        saveData = SaveData(this)
        //set light or dark mode from sharedPreferences
        if (saveData.lightMode == "true") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
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
        // ws://status.mobiblanc.tech/socket.io/?EIO=4&transport=websocket

    }

    fun setUpConnexion(url: String) {
        //Setup and create connexion
        scarlet =
            ApiUtilities.provideScarlet(url)
        webSocketService = ApiUtilities.getInstance(scarlet)
        //Service Shared Data
        sharedRepository = SharedRepository(webSocketService)
        sharedViewModel = ViewModelProvider(
            this,
            SharedViewModelFactory(sharedRepository)
        )[SharedViewModel::class.java]
        CoroutineScope(Dispatchers.IO).launch {
            sharedViewModel.handleConnexionState()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}