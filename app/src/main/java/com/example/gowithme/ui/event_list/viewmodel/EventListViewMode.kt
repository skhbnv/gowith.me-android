package com.example.gowithme.ui.event_list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gowithme.data.network.event_list.IEventListRepository

class EventListViewMode(
    private val repository: IEventListRepository
) : ViewModel() {

    private val eventListType = MutableLiveData<String>()

    val eventList = Transformations.switchMap(eventListType) {
        repository.getViewEvents(viewModelScope)
    }

    fun loadEvents() {
        eventListType.value = "asd"
    }

}