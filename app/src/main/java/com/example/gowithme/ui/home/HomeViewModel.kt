package com.example.gowithme.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.gowithme.data.models.response.CategoryResponse
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.data.network.ApiRepository
import com.example.gowithme.data.network.event.IEventRepository
import com.example.gowithme.responses.GeneralEvents
import com.example.gowithme.util.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.InputStream

class HomeViewModel(var repository: IEventRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _events = MutableLiveData<List<EventResponse>>()
    val eventsLD: LiveData<List<EventResponse>> get() = _events

    private val _eventCategories = MutableLiveData<List<CategoryResponse>>()
    val eventCategories: LiveData<List<CategoryResponse>> get() = _eventCategories

    private val _newEvents = MutableLiveData<List<EventResponse>>()
    val newEvents: LiveData<List<EventResponse>> get() = _newEvents

    private val _mostViewedEvents = MutableLiveData<List<EventResponse>>()
    val mostViewedEvents: LiveData<List<EventResponse>> get() = _mostViewedEvents


    fun getEvents() {
        _loading.value = true
        viewModelScope.launch {
            Log.d("taaag", "launch2")

            val eventCategoriesDeferred = async { repository.getEventCategories() }
            val newEventsDeferred = async { repository.getNewEvents() }
            val mostViewedEventsDeferred = async { repository.getMostViewedEvents() }


            when (val result = newEventsDeferred.await()) {
                is Result.Success -> {
                    Log.d("taaag", "Success ${result.data}")
                    _newEvents.value = result.data.results
                }
                is Result.Error -> {
                    Log.d("taaag", "Error ${result.exception}")
                }
            }

            when (val result = mostViewedEventsDeferred.await()) {
                is Result.Success -> {
                    Log.d("taaag", "Success ${result.data}")
                    _mostViewedEvents.value = result.data.results
                }

                is Result.Error -> {
                    Log.d("taaag", "Error ${result.exception}")
                }
            }

            when (val result = eventCategoriesDeferred.await()) {
                is Result.Success -> {
                    Log.d("taaag", "Success ${result.data}")
                    _eventCategories.value = result.data
                }

                is Result.Error -> {
                    Log.d("taaag", "Error ${result.exception}")
                }
            }

            _loading.value = false
        }
    }

}