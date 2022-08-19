package com.uptime.kuma.api

import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.lifecycle.LifecycleRegistry

class CustomSocketLifecycle(val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry()) :
    Lifecycle by lifecycleRegistry { init {
    lifecycleRegistry.onNext(Lifecycle.State.Started)
}
}