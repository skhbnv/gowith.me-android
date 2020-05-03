package com.example.gowithme.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.data.network.ApiRepository
import com.example.gowithme.data.network.event.IEventRepository
import com.example.gowithme.responses.GeneralEvents
import com.example.gowithme.util.Result
import kotlinx.coroutines.launch
import java.io.InputStream

class HomeViewModel(var repository: IEventRepository) : ViewModel() {

    private val _events = MutableLiveData<List<EventResponse>>()
    val eventsLD: LiveData<List<EventResponse>> get() = _events

    fun getEvents() {
        viewModelScope.launch {
            Log.d("taaag", "launch2")

            when (val result = repository.getEvents()) {
                is Result.Success -> {
                    Log.d("taaag", "Success ${result.data}")
                    _events.value = result.data.results
                }

                is Result.Error -> {
                    Log.d("taaag", "Error ${result.exception}")
                }
            }
        }
    }

}