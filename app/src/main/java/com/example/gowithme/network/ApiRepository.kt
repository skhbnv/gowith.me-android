package com.example.gowithme.network

import com.example.gowithme.responses.Event
import com.example.gowithme.responses.GeneralEvents
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApiRepository {
    private val apiInstance = RetrofitApi.instance

    fun getEvents(onSuccess: (List<GeneralEvents>?)->Unit, onFailure: (t: Throwable)->Unit){
        apiInstance.getEvents().enqueue(object: Callback<List<GeneralEvents>> {
            override fun onFailure(call: Call<List<GeneralEvents>>, t: Throwable) {
                onFailure(t)
            }

            override fun onResponse(call: Call<List<GeneralEvents>>, response: Response<List<GeneralEvents>>
            ) {
                onSuccess(response.body())

            }

        })
    }
}