package com.uptime.kuma.views.mainActivity

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Process
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
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
import com.uptime.kuma.views.dashbord.DashboardFragment
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
        lateinit var instance: MainActivity
    }

    //Notification Attributes
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "0"
    private val description = "Test notification"

    private lateinit var sharedRepository: SharedRepository


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        saveData = SaveData(applicationContext)
        //set light or dark mode from sharedPreferences
        if (saveData.lightMode == "true") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instance = this

        createNotificationChannel()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        //instance mainViewModel
        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        //instance Helper class languageSettings
        CoroutineScope(Dispatchers.IO).launch {
            languageSettings = LanguageSettings(applicationContext)
            mainActivityViewModel.setAppLocale(
                applicationContext,
                languageSettings.language.toString()
            )
        }

        // ws://status.mobiblanc.tech/socket.io/?EIO=4&transport=websocket
//        status.mobiblanc.tech
//        ZS}3Dn@:sq@W<=R)U3

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

    override fun onBackPressed() {
        return
    }

    fun restartApplication() {
        val intent = this.baseContext.packageManager.getLaunchIntentForPackage(
            this.baseContext.packageName
        )
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        Process.killProcess(Process.myPid())
        System.exit(0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("RemoteViewLayout")
    fun sendNotification(elm: String) {
        val intent = Intent(this, DashboardFragment::class.java)

        val pIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        val n: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Alert")
            .setContentText(elm)
            .setSmallIcon(com.uptime.kuma.R.drawable.icon)
            .setContentIntent(pIntent)
            .setAutoCancel(true)
            .addAction(R.drawable.icon, "Call", pIntent)
            .addAction(R.drawable.icon, "More", pIntent)
            .addAction(R.drawable.icon, "And more", pIntent).build()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(channelId.toInt(), n)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name"
            val descriptionText = "desc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}