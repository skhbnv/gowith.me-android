package com.example.gowithme.di

import android.content.SharedPreferences
import android.util.Log
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
import com.example.gowithme.data.network.profile.ProfileService
import com.example.gowithme.util.NetworkConst.HEADER_AUTH
import com.example.gowithme.util.NetworkConst.TOKEN_PREFIX
import com.example.gowithme.util.PreferencesConst
import okhttp3.Interceptor
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

    single {
        Interceptor { chain ->
            val token = get<SharedPreferences>(named(PreferencesConst.TOKEN_PREFERENCES)).getString(PreferencesConst.ACCESS_TOKEN, "") ?: ""
            val request = chain.request()
            Log.d("http", "-> request [${request.method()}], ${request.url()}, ${request.body().toString()}")
            val newRequest = request.newBuilder().apply {
                if (token.isNotBlank()) {
                    addHeader(HEADER_AUTH, TOKEN_PREFIX + token)
                }
            }.build()
            val response = chain.proceed(newRequest)
            Log.d("http", "<- response [${request.method()}], code ${response.code()}, ${request.url()}, ${if (!response.isSuccessful) response.body()?.string() else "true"}, ")
            Log.d("http", "--------------------")
            return@Interceptor response
        }
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get())
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

    single<ProfileService> {
        get<Retrofit>(named(RETROFIT_NAME)).create(ProfileService::class.java)
    }
}

