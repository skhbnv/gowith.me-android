package com.example.gowithme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gowithme.data.network.main.IMainRepository

class MainViewModel(
    private val repository: IMainRepository
): ViewModel() {

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> get() = _loginState

    init {
        _loginState.value = repository.getAccessToken().isNotBlank()

        // TODO add token verify
    }

    fun logout() {
        repository.removeTokens()
        _loginState.value = false
    }
}