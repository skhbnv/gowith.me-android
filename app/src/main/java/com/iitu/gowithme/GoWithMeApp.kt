package com.iitu.gowithme

import android.app.Application
import com.iitu.gowithme.di.networkModule
import com.iitu.gowithme.di.repoModule
import com.iitu.gowithme.di.tokenModule
import com.iitu.gowithme.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GoWithMeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf(networkModule, tokenModule, repoModule, viewModelModule))
        }
    }

}