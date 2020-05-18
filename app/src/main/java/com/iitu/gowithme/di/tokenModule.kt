package com.iitu.gowithme.di

import android.content.Context
import android.content.SharedPreferences
import com.iitu.gowithme.data.network.token.TokenService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.iitu.gowithme.BuildConfig
import com.iitu.gowithme.data.network.token.TokenRepositoryImpl
import com.iitu.gowithme.util.PreferencesConst
import org.koin.android.ext.koin.androidContext

private const val RETROFIT_NAME = "without_client"
private const val PREFERENCES_NAME = "Token Preferences"

val tokenModule = module {

    single<Retrofit>(named(RETROFIT_NAME)) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<TokenService> {
        get<Retrofit>(named(RETROFIT_NAME)).create(TokenService::class.java)
    }

    single<SharedPreferences>(named(PreferencesConst.TOKEN_PREFERENCES)) {
        androidContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    single {
        TokenRepositoryImpl(get(), get(named(PreferencesConst.TOKEN_PREFERENCES)))
    }

}

