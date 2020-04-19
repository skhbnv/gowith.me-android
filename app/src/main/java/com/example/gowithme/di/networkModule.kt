package com.example.gowithme.di

import com.example.gowithme.data.network.ApiService
import com.example.gowithme.data.network.token.TokenAuthenticator
import com.example.gowithme.data.network.token.TokenRepositoryImpl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import com.example.gowithme.BuildConfig
import com.example.gowithme.data.network.auth.AuthService
import com.example.gowithme.data.network.event.EventService
import org.koin.core.qualifier.named
import retrofit2.converter.gson.GsonConverterFactory

private const val RETROFIT_NAME = "client"

val networkModule = module {

    single<Retrofit>(named(RETROFIT_NAME)) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get<OkHttpClient>())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .authenticator(TokenAuthenticator(get<TokenRepositoryImpl>()))
            .build()
    }

    single<ApiService> {
        get<Retrofit>(named(RETROFIT_NAME)).create(ApiService::class.java)
    }

    single<AuthService> {
        get<Retrofit>(named(RETROFIT_NAME)).create(AuthService::class.java)
    }

    single<EventService> {
        get<Retrofit>(named(RETROFIT_NAME)).create(EventService::class.java)
    }
}

