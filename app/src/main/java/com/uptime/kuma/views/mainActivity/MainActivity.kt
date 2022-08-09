package com.uptime.kuma.views.mainActivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tinder.scarlet.Scarlet
import com.uptime.kuma.R
import com.uptime.kuma.api.ConnexionInterface

class MainActivity : AppCompatActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var navController: NavController
        lateinit var scarlet: Scarlet
        lateinit var webSocketService: ConnexionInterface
        lateinit var sharedViewModel: SharedViewModel
        lateinit var languageSettings: LanguageSettings

    }
    private lateinit var apparenceReglageViewModel: ApparenceReglageViewModel

    private lateinit var sharedRepository: SharedRepository

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        languageSettings= LanguageSettings(this)

 apparenceReglageViewModel= ViewModelProvider(this).get(ApparenceReglageViewModel::class.java)
        apparenceReglageViewModel.setAppLocale(this, languageSettings.language.toString())



//        setFullScreen(window)
        lightStatusBar(window)

        //Setup and create connexion
        scarlet = ApiUtilities.provideScarlet(application)
        webSocketService = ApiUtilities.getInstance(scarlet)

        //Service Shared Data
        sharedRepository = SharedRepository(webSocketService)
        sharedViewModel = ViewModelProvider(
            this,
            SharedViewModelFactory(sharedRepository)
        )[SharedViewModel::class.java]

        sharedViewModel.handleConnexionState(this, lifecycleScope = lifecycleScope)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}