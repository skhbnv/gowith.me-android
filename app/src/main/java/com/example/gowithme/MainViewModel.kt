package com.example.gowithme

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gowithme.data.network.main.IMainRepository
import com.example.gowithme.util.Result
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: IMainRepository
): ViewModel() {

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> get() = _loginState

    val userInfo get() = repository.getMyInfoLocal()

    init {
        _loginState.value = repository.getAccessToken().isNotBlank()
        loadUserInfo()
        // TODO add token verify
    }

    fun checkLoginStatus() {
        _loginState.value = repository.getAccessToken().isNotBlank()
    }

    private fun loadUserInfo() {
        viewModelScope.launch { 
            when(val result = repository.getMyInfo()) {
                is Result.Success -> {
                    repository.saveMyInfo(result.data)
                }
                is Result.Error -> {
                    Log.d("taaag", "MainViewModel getUserInfo ${result.exception}")
                }
            }
        }
    }

    fun logout() {
        repository.removeTokens()
        _loginState.value = false
    }
}