package com.example.gowithme.ui.event_list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gowithme.data.network.event_list.EventListType
import com.example.gowithme.data.network.event_list.IEventListRepository

class EventListViewMode(
    private val repository: IEventListRepository
) : ViewModel() {

    private val _eventListType = MutableLiveData<EventListType>()

    val eventList = Transformations.switchMap(_eventListType) {
        repository.getEventList(viewModelScope, it)
    }

    fun loadEvents(eventListType: EventListType) {
        _eventListType.value = eventListType
    }

}