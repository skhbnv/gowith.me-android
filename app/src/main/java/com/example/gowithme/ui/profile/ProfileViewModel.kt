package com.example.gowithme.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gowithme.data.models.response.MyInfoResponse
import com.example.gowithme.data.network.profile.IProfileRepository
import com.example.gowithme.util.Result
import kotlinx.coroutines.launch

class ProfileViewModel(private var repository: IProfileRepository) : ViewModel() {

    private val _profileInfo = MutableLiveData<MyInfoResponse>()
    val profileInfo: LiveData<MyInfoResponse> get() = _profileInfo

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun getMyInfo() {
        _loading.value= true
        viewModelScope.launch {
            when (val result = repository.getMyInfo()) {
                is Result.Success -> {
                    Log.d("taaag", "Success")
                    _profileInfo.value = result.data
                }
                is Result.Error -> {
                    Log.d("taaag", "Error ${result.exception}")

                }
            }
            _loading.value= false
        }
    }

}