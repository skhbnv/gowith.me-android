package com.example.gowithme.data.network

import com.example.gowithme.data.models.request.LoginRequest
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.responses.GeneralEvents
import com.example.gowithme.util.apiCall
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.io.InputStream


class ApiRepository(val apiService: ApiService? = null) {

    fun loadJsonFromAsset(inst: InputStream) : String?{
        var json: String? = null
        try {
            val size = inst.available()
            val buffer = ByteArray(size)
            inst.read(buffer)
            inst.close()
            json = String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json
    }

    suspend fun login(request: LoginRequest) = apiCall { apiService!!.login(request) }


    suspend fun getEvents() = apiCall { apiService!!.getEvents() }



}

