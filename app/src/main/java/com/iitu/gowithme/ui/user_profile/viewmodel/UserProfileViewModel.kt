package com.iitu.gowithme.ui.user_profile.viewmodel

import androidx.lifecycle.*
import com.iitu.gowithme.data.models.response.ProfileInfoResponse
import com.iitu.gowithme.data.network.user.IUserProfileRepository
import com.iitu.gowithme.data.network.user.UserListType
import com.iitu.gowithme.util.Result
import kotlinx.coroutines.launch
import java.lang.Exception

class UserProfileViewModel(
    private val repository: IUserProfileRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _eventId = MutableLiveData<UserListType>()

    val usersList = Transformations.switchMap(_eventId) {
        repository.getUserList(it, viewModelScope)
    }

    fun getEventSubscribers(listType: UserListType) {
        _eventId.value = listType
    }

    fun removeUser(eventId: Int, userId: Int) {
        viewModelScope.launch {
            when(val result = repository.removeUser(eventId, userId)) {
                is Result.Success -> {
                    _profileDetailsUI.value = ProfileDetailsUI.RemoveUserSuccess
                }
                is Result.Error -> {

                }
            }
        }
    }

    // ------------------------- Profile details ------------------------------------

    private val _profileInfo = MutableLiveData<ProfileInfoResponse>()
    val profileInfo: LiveData<ProfileInfoResponse> get() = _profileInfo

    private val _profileDetailsUI = MutableLiveData<ProfileDetailsUI>()
    val profileDetailsUI: LiveData<ProfileDetailsUI> get() = _profileDetailsUI

    fun getUserProfileInfo(userId: Int) {
        _loading.value = true
        viewModelScope.launch {
            when(val result = repository.getUserProfileInfo(userId)) {
                is Result.Success -> {
                    _profileInfo.value = result.data
                }
                is Result.Error -> {
                    _profileDetailsUI.value = ProfileDetailsUI.ProfileDetailsLoadError(result.exception)
                }
            }
            _loading.value = false
        }
    }

    fun subscribeOrUnSubscribe(userId: Int) {
        viewModelScope.launch {
            val result = if(profileInfo.value?.isMeFollower == true) {
                repository.unSubscribeFromUser(userId)
            } else {
                repository.subscribeOnUser(userId)
            }
            when(result) {
                is Result.Success -> {
                    getUserProfileInfo(userId)
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
    object RemoveUserSuccess : ProfileDetailsUI()
    data class ProfileDetailsLoadError(val exception: Exception) : ProfileDetailsUI()

}