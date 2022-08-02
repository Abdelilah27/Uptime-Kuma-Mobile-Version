package com.uptime.kuma.views.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.StreamAdapter
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentSplashBinding
import com.uptime.kuma.services.ConnexionService
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    companion object {
        private const val ECHO_URL =
            "ws://status.mobiblanc.tech/socket.io/?EIO=4&transport=websocket"
    }

    private lateinit var webSocketService: ConnexionService
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_splash, container, false)
////        Handler(Looper.myLooper()!!).postDelayed({
////            MainActivity.navController.navigate(R.id.bienvenueFragment)
////        }, 2500)
//        return view
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSplashBinding.bind(view)
        setupWebSocketService()
        observeConnection()
        binding.image.setOnClickListener {
            sendQuery("40")
        }
    }

    private fun sendQuery(param: String) {
        webSocketService.sendMessage(param)
    }

    private fun setupWebSocketService() {
        webSocketService = provideWebSocketService(
            scarlet = provideScarlet(
                client = provideOkhttp(),
                lifecycle = provideLifeCycle(),
                streamAdapterFactory = provideStreamAdapterFactory(),
            )
        )
    }

    @SuppressLint("CheckResult")
    private fun observeConnection() {
        webSocketService.observeConnection()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d("observeConnection", response.toString())
            }, { error ->
                Log.e("observeConnection", error.message.orEmpty())
            })
    }

    private fun provideWebSocketService(scarlet: Scarlet) =
        scarlet.create(ConnexionService::class.java)

    // TODO: 14/10/21 implement dependency injection and move it from activity
    private fun provideScarlet(
        client: OkHttpClient,
        lifecycle: Lifecycle,
        streamAdapterFactory: StreamAdapter.Factory,
    ) =
        Scarlet.Builder()
            .webSocketFactory(client.newWebSocketFactory(ECHO_URL))
            .lifecycle(lifecycle)
            .addStreamAdapterFactory(streamAdapterFactory)
            .build()

    // TODO: 14/10/21 implement dependency injection and move it from activity
    private fun provideOkhttp() =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()

    // TODO: 14/10/21 implement dependency injection and move it from activity
    private fun provideLifeCycle() =
        AndroidLifecycle.ofApplicationForeground(activity!!.application)


    // TODO: 14/10/21 implement dependency injection and move it from activity
    private fun provideStreamAdapterFactory() = RxJava2StreamAdapterFactory()
}