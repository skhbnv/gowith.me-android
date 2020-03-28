package com.example.gowithme.util

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.Exception

sealed class Result<T> {
    data class Success<T>(val data: T): Result<T>()
    data class Error<T>(val exception: Exception): Result<T>()
}

suspend fun <T : Any>  apiCall (call : suspend () -> T) = try {
    Result.Success(call())
} catch (e: Exception) {
    Result.Error<T>(e)
}

