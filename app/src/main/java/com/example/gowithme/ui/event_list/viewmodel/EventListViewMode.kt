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

    private val _eventListType = MutableLiveData<Pair<EventListType, Int>>()

    val eventList = Transformations.switchMap(_eventListType) {
        repository.getEventList(viewModelScope, it.first, it.second)
    }

    fun loadEvents(eventListType: EventListType, id: Int) {
        _eventListType.value = eventListType to id
    }

}