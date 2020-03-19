package com.example.gowithme.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gowithme.network.ApiRepository
import com.example.gowithme.responses.ProfileInfo

class ProfileViewModel(var repository: ApiRepository) : ViewModel() {

    var profileInfo = MutableLiveData<ProfileInfo>()

    class ProfileFactory(private var repository: ApiRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileViewModel(repository) as T
        }
    }
}