package com.example.gowithme.network

import com.example.gowithme.responses.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApiRepository {
    private val apiInstance = RetrofitApi.instance

    fun getEvents(onSuccess: (List<Event>?)->Unit, onFailure: (t: Throwable)->Unit){
        apiInstance.getEvents().enqueue(object: Callback<List<Event>> {
            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                onFailure(t)
            }

            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                onSuccess(response.body())
            }

        })
    }
}


