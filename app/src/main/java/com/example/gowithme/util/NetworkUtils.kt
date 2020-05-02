package com.example.gowithme.util

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

