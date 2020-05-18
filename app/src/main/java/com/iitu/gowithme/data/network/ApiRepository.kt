package com.iitu.gowithme.data.network

import com.iitu.gowithme.data.models.request.LoginRequest
import com.iitu.gowithme.util.apiCall
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

