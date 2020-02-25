package com.example.gowithme.ui.dashboard

import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gowithme.network.ApiRepository
import com.example.gowithme.responses.Event

class DashboardViewModel(private var repository: ApiRepository) : ViewModel() {

    val events = MutableLiveData<List<Event>>()

    fun getEvents() {
        repository.getEvents(
            onSuccess = { list ->
                events.value = list
                d("___", list!![0].date)
            },
            onFailure = { errorMessage ->
                d("___", errorMessage.message)
            }
        )
    }

    class DashboardFactory(private var repository: ApiRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DashboardViewModel(repository) as T
        }
    }
}