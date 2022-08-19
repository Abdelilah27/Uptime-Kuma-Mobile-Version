package com.uptime.kuma.views.mainActivity

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tinder.scarlet.Scarlet
import com.uptime.kuma.R
import com.uptime.kuma.api.ApiUtilities
import com.uptime.kuma.api.ConnexionInterface
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem
import com.uptime.kuma.repository.SharedRepository
import com.uptime.kuma.service.sharedData.SharedViewModel
import com.uptime.kuma.service.sharedData.SharedViewModelFactory
import com.uptime.kuma.utils.LanguageSettings
import com.uptime.kuma.utils.SaveData
import com.uptime.kuma.utils.UpdateData
import com.uptime.kuma.views.dashbord.DashboardFragment
import com.uptime.kuma.views.login.LoginFragment

class MainActivity : AppCompatActivity() ,UpdateData{

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var navController: NavController
        lateinit var languageSettings: LanguageSettings
        lateinit var scarlet: Scarlet
        lateinit var webSocketService: ConnexionInterface
        lateinit var instance :MainActivity

        //        lateinit var sharedViewModel: SharedViewModel
        lateinit var mainActivityViewModel: MainActivityViewModel
        lateinit var saveData: SaveData
    }

    private lateinit var sharedRepository: SharedRepository

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        saveData = SaveData(this)
        instance =this

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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

//        setFullScreen(window)
//        lightStatusBar(window)

        LoginFragment.socketLiveData.observe(this, Observer {
            //Setup and create connexion
            scarlet =
                ApiUtilities.provideScarlet("ws://status.mobiblanc.tech/socket.io/?EIO=4&transport=websocket")
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

    @SuppressLint("RemoteViewLayout")
    fun sendNotification(elm:String){
        val intent = Intent(this, DashboardFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // RemoteViews are used to use the content of
        // some different layout apart from the current activity layout
        val contentView = RemoteViews(packageName, R.layout.notification_layout)

        // checking if android version is greater than oreo(API 26) or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setContent(contentView)
                .setContentIntent(pendingIntent)
        } else {

            builder = Notification.Builder(this)
                .setContent(contentView)
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())
    }

    override fun onReceivedData(data: MonitorStatusItem) {
        sendNotification(data.msg?:"")
    }
}