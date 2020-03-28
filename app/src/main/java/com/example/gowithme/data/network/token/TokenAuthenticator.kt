package com.example.gowithme.data.network.token

import android.util.Log
import com.example.gowithme.util.Result
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import com.example.gowithme.util.NetworkConst.HEADER_AUTH
import com.example.gowithme.util.NetworkConst.TOKEN_PREFIX

class TokenAuthenticator(
    private val repository: TokenRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d("taaag", "---------")

        if (!isRequestWithAccessToken(response)) {
            Log.d("taaag", "isRequestWithAccessToken")
            return null
        }
        return runBlocking {

            when (val result = repository.refreshToken(
                repository.getRefreshToken())) {
                is Result.Success -> {
                    repository.saveToken(result.data.access)
                }
                is Result.Error -> {
                    return@runBlocking null
                }
            }

            return@runBlocking newRequestWithAccessToken(response.request(), repository.getToken())
        }
    }


    private fun isRequestWithAccessToken(response: Response) =
        response.request().header(HEADER_AUTH)?.startsWith(TOKEN_PREFIX) ?: false

    private fun newRequestWithAccessToken(request: Request, accessToken: String) =
        request.newBuilder()
            .header(HEADER_AUTH, TOKEN_PREFIX + accessToken)
            .build()

}