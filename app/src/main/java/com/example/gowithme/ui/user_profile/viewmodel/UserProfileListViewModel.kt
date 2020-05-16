package com.example.gowithme.ui.user_profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gowithme.data.network.user.IUserProfileRepository

class UserProfileListViewModel(
    private val repository: IUserProfileRepository
) : ViewModel() {

    private val _eventId = MutableLiveData<Int>()

    val usersList = Transformations.switchMap(_eventId) {
        repository.getEventSubscribersList(it, viewModelScope)
    }

    fun getEventSubscribers(eventId: Int) {
        _eventId.value = eventId
    }

}