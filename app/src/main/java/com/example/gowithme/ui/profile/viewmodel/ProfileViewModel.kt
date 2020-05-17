package com.example.gowithme.ui.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.data.models.response.ProfileInfoResponse
import com.example.gowithme.data.network.profile.IProfileRepository
import com.example.gowithme.util.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProfileViewModel(private var repository: IProfileRepository) : ViewModel() {

    private val _profileInfo = MutableLiveData<ProfileInfoResponse>()
    val profileInfo: LiveData<ProfileInfoResponse> get() = _profileInfo

    private val _viewedEvents = MutableLiveData<List<EventResponse>>()
    val viewedEvents: LiveData<List<EventResponse>> get() = _viewedEvents

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun getMyInfo() {
        _loading.value= true
        viewModelScope.launch {

            val myInfoDeferred = async { repository.getMyInfo() }
            val myViewedEventsDeferred = async { repository.getViewEvents() }

            when (val result = myInfoDeferred.await()) {
                is Result.Success -> {
                    Log.d("taaag", "Success")
                    _profileInfo.value = result.data
                }
                is Result.Error -> {
                    Log.d("taaag", "Error ${result.exception}")
                }
            }

            when (val result = myViewedEventsDeferred.await()) {
                is Result.Success -> {
                    Log.d("taaag", "Success")
                    _viewedEvents.value = result.data.results
                }
                is Result.Error -> {
                    Log.d("taaag", "Error ${result.exception}")
                }
            }
            _loading.value= false
        }
    }


}