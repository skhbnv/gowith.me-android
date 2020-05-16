package com.example.gowithme.ui.user_profile.viewmodel

import androidx.lifecycle.*
import com.example.gowithme.data.models.response.ProfileInfoResponse
import com.example.gowithme.data.network.user.IUserProfileRepository
import com.example.gowithme.util.Result
import kotlinx.coroutines.launch
import java.lang.Exception

class UserProfileViewModel(
    private val repository: IUserProfileRepository
) : ViewModel() {

    private val _eventId = MutableLiveData<Int>()

    val usersList = Transformations.switchMap(_eventId) {
        repository.getEventSubscribersList(it, viewModelScope)
    }

    fun getEventSubscribers(eventId: Int) {
        _eventId.value = eventId
    }

    // ------------------------- Profile details ------------------------------------

    private val _profileInfo = MutableLiveData<ProfileInfoResponse>()
    val profileInfo: LiveData<ProfileInfoResponse> get() = _profileInfo

    private val _profileDetailsUI = MutableLiveData<ProfileDetailsUI>()
    val profileDetailsUI: LiveData<ProfileDetailsUI> get() = _profileDetailsUI

    fun getUserProfileInfo(userId: Int) {
        viewModelScope.launch {
            when(val result = repository.getUserProfileInfo(userId)) {
                is Result.Success -> {
                    _profileInfo.value = result.data
                }
                is Result.Error -> {
                    _profileDetailsUI.value = ProfileDetailsUI.ProfileDetailsLoadError(result.exception)
                }
            }
        }
    }
}

sealed class ProfileDetailsUI {

    data class ProfileInfoLoadSuccess(val profileInfo: ProfileInfoResponse) : ProfileDetailsUI()
    data class ProfileDetailsLoadError(val exception: Exception) : ProfileDetailsUI()

}