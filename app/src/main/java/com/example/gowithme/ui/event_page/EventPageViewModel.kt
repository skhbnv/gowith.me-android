package com.example.gowithme.ui.event_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gowithme.data.network.ApiRepository
import com.example.gowithme.responses.DetailEvents
import com.example.gowithme.responses.GeneralEvents

class EventPageViewModel(var repository: ApiRepository) : ViewModel(){
    val selectedDetailEvents = MutableLiveData<DetailEvents>()
    val selectedGeneralEvents = MutableLiveData<GeneralEvents>()

    class EventPageFactory(private var repository: ApiRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EventPageViewModel(repository) as T
        }
    }
}