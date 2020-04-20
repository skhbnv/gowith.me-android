package com.example.gowithme.ui.auth.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gowithme.data.models.request.LoginRequest
import com.example.gowithme.data.network.auth.IAuthRepository
import com.example.gowithme.util.Result
import kotlinx.coroutines.launch
import java.lang.Exception

class AuthViewModel(private val repository: IAuthRepository): ViewModel() {

    private val _loginUI = MutableLiveData<LoginUI>()
    val loginUI: LiveData<LoginUI> get() = _loginUI

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun login(request: LoginRequest) {
        _loading.value = true
        viewModelScope.launch {
            when(val result = repository.login(request)) {
                is Result.Success -> {
                    _loginUI.value = LoginUI.Login
                }
                is Result.Error -> {
                    _loginUI.value = LoginUI.Error(result.exception)
                }
            }
            _loading.value = false
        }
    }

}

sealed class LoginUI {

    object Login: LoginUI()
    data class Error(val exception: Exception): LoginUI()
}