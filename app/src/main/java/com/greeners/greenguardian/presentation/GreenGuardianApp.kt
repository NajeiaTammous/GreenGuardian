package com.greeners.greenguardian.presentation

import android.app.Application
import com.greeners.greenguardian.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class GreenGuardianApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GreenGuardianApp)
            modules(appModule())
        }
    }

}
