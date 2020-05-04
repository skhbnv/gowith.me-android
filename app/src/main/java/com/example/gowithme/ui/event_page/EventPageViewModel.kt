package com.example.gowithme.ui.event_page

import androidx.lifecycle.*
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.data.network.event.IEventRepository
import com.example.gowithme.util.Result
import kotlinx.coroutines.launch
import java.lang.Exception

class EventPageViewModel(var repository: IEventRepository) : ViewModel(){

    private val _eventDetailsUI = MutableLiveData<EventPageUI>()
    val eventDetailsUI: LiveData<EventPageUI> get() = _eventDetailsUI

    fun getEventDetails(id: Int) {
        viewModelScope.launch {
            when(val result = repository.getEventDetails(id)) {
                is Result.Success -> {
                    _eventDetailsUI.value = EventPageUI.EventLoaded(result.data)
                }
                is Result.Error -> {
                    _eventDetailsUI.value = EventPageUI.EventLoadError(result.exception)
                }
            }
        }
    }

    fun subscribeOnEvent(id: Int) {
        viewModelScope.launch {
            when(val result = repository.subscribeOnEvent(id)) {
                is Result.Success -> {
                    _eventDetailsUI.value = EventPageUI.OnEventSubscribeSuccess
                }
                is Result.Error -> {
                    _eventDetailsUI.value = EventPageUI.OnEventSubscribeError(result.exception)
                }
            }
        }
    }
}

sealed class EventPageUI {

    data class EventLoaded(val event: EventResponse) : EventPageUI()
    data class EventLoadError(val exception: Exception) : EventPageUI()

    object OnEventSubscribeSuccess : EventPageUI()
    data class OnEventSubscribeError(val exception: Exception) : EventPageUI()

}