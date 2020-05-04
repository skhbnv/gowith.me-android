package com.example.gowithme.ui.auth.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.gowithme.data.models.request.CheckPhoneRequest
import com.example.gowithme.data.models.request.ConfirmPhoneRequest
import com.example.gowithme.data.models.request.LoginRequest
import com.example.gowithme.data.models.request.RegisterRequest
import com.example.gowithme.data.models.response.CategoryResponse
import com.example.gowithme.data.models.response.CheckPhoneResponse
import com.example.gowithme.data.models.response.LoginResponse
import com.example.gowithme.data.network.auth.IAuthRepository
import com.example.gowithme.util.Result
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.lang.Exception

class AuthViewModel(private val repository: IAuthRepository): ViewModel() {

    private val _loginUI = MutableLiveData<LoginUI>()
    val loginUI: LiveData<LoginUI> get() = _loginUI

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _checkPhone = MutableLiveData<CheckPhoneResponse>()
    val checkPhone: LiveData<CheckPhoneResponse> get() = _checkPhone

    private val _confirmPhone = MutableLiveData<Int>()
    val confirmPhone: LiveData<Int> get() = _confirmPhone

    private val _register = MutableLiveData<LoginResponse>()
    val register: LiveData<LoginResponse> get() = _register

    private var phone = ""

    private val _categories = MutableLiveData<List<CategoryResponse>>()
    val categories: LiveData<List<CategoryResponse>> get() = _categories

    val checkedCategories = ArrayList<CategoryResponse>()

    var firstName = ""
    var lastName = ""
    var tUserName = ""
    var password = ""
    var confPassword = ""

    fun getCategories() {
        _loading.value = true
        viewModelScope.launch {
            when (val result = repository.getEventCategories()) {
                is Result.Success -> {
                    _categories.value = result.data
                }
                is Result.Error -> {

                }
            }
            _loading.value = false
        }
    }

    fun checkCategory(index: Int) {
        Log.d("taaag", "checkCategory index = $index")
        _categories.value?.get(index)?.apply{
            this.isChecked = !this.isChecked
        }
        Log.d("taaag", "checkCategory index = $index")
        checkedCategories.clear()
        checkedCategories.addAll(_categories.value?.filter { it.isChecked } ?: emptyList())
    }

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

    fun checkPhone(phone: String) {
        viewModelScope.launch {
            when(val result = repository.checkPhone(CheckPhoneRequest(phone))) {
                is Result.Success -> {
                    this@AuthViewModel.phone = phone
                    _checkPhone.value = result.data
                }
                is Result.Error -> {

                }
            }
        }
    }

    fun confirmPhone(code: String) {
        viewModelScope.launch {
            when(val result = repository.confirmPhone(ConfirmPhoneRequest(phone, code))) {
                is Result.Success -> {
                    Log.d("taaag", "confirmPhone Success")
                    _confirmPhone.value = 5
                }
                is Result.Error -> {
                    Log.d("taaag", "confirmPhone Error ${result.exception}")

                }
            }
        }
    }

    fun register() {
        viewModelScope.launch {
            val request = RegisterRequest(
                phone = phone,
                firstName = firstName,
                lastName = lastName,
                telegramUsername = tUserName,
                password = password,
                favoriteCategory = checkedCategories.map { it.id }
            )
            when(val result = repository.register(request)) {
                is Result.Success -> {
                    _register.value = result.data
                }
                is Result.Error -> {

                }
            }
        }
    }

}

sealed class LoginUI {

    object Login: LoginUI()
    data class Error(val exception: Exception): LoginUI()
}