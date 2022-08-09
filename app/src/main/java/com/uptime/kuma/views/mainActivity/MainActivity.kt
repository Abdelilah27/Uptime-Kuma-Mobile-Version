package com.uptime.kuma.views.mainActivity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.uptime.kuma.R
import com.uptime.kuma.utils.LanguageSettings
import com.uptime.kuma.views.apparence.ApparenceReglageFragment
import com.uptime.kuma.views.apparence.ApparenceReglageViewModel

class MainActivity : AppCompatActivity() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var navController: NavController
        lateinit var languageSettings: LanguageSettings

    }
    private lateinit var apparenceReglageViewModel: ApparenceReglageViewModel

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        languageSettings= LanguageSettings(this)


//        val pref = getPreferences(Context.MODE_PRIVATE)
//        val editor=pref.edit()
//
//        editor.putString("Language",ApparenceReglageFragment.language)
//
//        editor.commit()
//
//        val language=pref.getString("Language","")
//        Log.d("language", "onCreate: " +language)
        apparenceReglageViewModel= ViewModelProvider(this).get(ApparenceReglageViewModel::class.java)
        apparenceReglageViewModel.setAppLocale(this, languageSettings.language.toString())


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

    }
}