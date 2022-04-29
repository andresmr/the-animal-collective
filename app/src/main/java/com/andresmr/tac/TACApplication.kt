package com.andresmr.tac

import android.app.Application
import org.hisp.dhis.android.core.D2Configuration
import org.hisp.dhis.android.core.D2Manager.blockingInstantiateD2


class TACApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initD2()
    }

    private fun initD2() {
        val configuration = D2Configuration.builder()
            .appName("The Animal Collective")
            .appVersion("0.0.1")
            .readTimeoutInSeconds(30)
            .connectTimeoutInSeconds(30)
            .writeTimeoutInSeconds(30)
            .context(applicationContext)
            .build()
        blockingInstantiateD2(configuration)
    }
}