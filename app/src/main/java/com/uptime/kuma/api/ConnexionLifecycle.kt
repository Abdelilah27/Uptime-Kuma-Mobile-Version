package com.uptime.kuma.api

import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.ShutdownReason

object ConnexionLifecycle {
    fun closeConnexion() {
        ApiUtilities.myLifecycle.lifecycleRegistry.onNext(
            Lifecycle.State.Stopped.WithReason(
                ShutdownReason.GRACEFUL
            )
        )
    }

    fun openConnexion() {
        ApiUtilities.myLifecycle.lifecycleRegistry.onNext(Lifecycle.State.Started)
    }

}