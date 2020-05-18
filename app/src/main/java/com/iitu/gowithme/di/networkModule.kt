package com.iitu.gowithme.di

import android.content.SharedPreferences
import android.util.Log
import com.iitu.gowithme.data.network.ApiService
import com.iitu.gowithme.data.network.token.TokenAuthenticator
import com.iitu.gowithme.data.network.token.TokenRepositoryImpl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import com.iitu.gowithme.BuildConfig
import com.iitu.gowithme.data.network.auth.AuthService
import com.iitu.gowithme.data.network.event.EventService
import com.iitu.gowithme.data.network.event_list.EventListService
import com.iitu.gowithme.data.network.profile.ProfileService
import com.iitu.gowithme.data.network.user.UserProfileService
import com.iitu.gowithme.util.NetworkConst.HEADER_AUTH
import com.iitu.gowithme.util.NetworkConst.TOKEN_PREFIX
import com.iitu.gowithme.util.PreferencesConst
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
                Log.d("http", "token $token")
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

    single<EventListService> {
        get<Retrofit>(named(RETROFIT_NAME)).create(EventListService::class.java)
    }

    single<UserProfileService> {
        get<Retrofit>(named(RETROFIT_NAME)).create(UserProfileService::class.java)
    }
}

