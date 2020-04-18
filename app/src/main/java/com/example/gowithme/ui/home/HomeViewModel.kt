package com.example.gowithme.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.data.network.ApiRepository
import com.example.gowithme.responses.GeneralEvents
import com.example.gowithme.util.Result
import kotlinx.coroutines.launch
import java.io.InputStream

class HomeViewModel(var repository: ApiRepository) : ViewModel() {

    init {
        Log.d("taaag", "init")
    }

    private val _events = MutableLiveData<List<EventResponse>>()
    val eventsLD: LiveData<List<EventResponse>> get() = _events

    fun getEvents() {
        viewModelScope.launch {
            Log.d("taaag", "launch2")

            val res = repository.getEvents()
            when (res) {
                is Result.Success -> {
                    Log.d("taaag", "Success ${res.data}")
                    _events.value = res.data
                }

                is Result.Error -> {
                    Log.d("taaag", "Error ${res.exception}")

                }
            }
        }
    }

    val events = MutableLiveData<List<GeneralEvents>>()
    fun loadJsonFromAsset(inst: InputStream) = repository.loadJsonFromAsset(inst)

    class HomeFactory(var repo : ApiRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(repo) as T
        }
    }
}