package com.example.gowithme

import android.app.Application
import com.example.gowithme.di.networkModule
import com.example.gowithme.di.repoModule
import com.example.gowithme.di.tokenModule
import com.example.gowithme.di.viewModelModule
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