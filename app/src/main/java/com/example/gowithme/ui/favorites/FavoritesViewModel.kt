package com.example.gowithme.ui.favorites

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gowithme.network.ApiRepository
import com.example.gowithme.responses.GeneralEvents

class FavoritesViewModel(private var repository: ApiRepository): ViewModel() {
    val events = MutableLiveData<List<GeneralEvents>>()

    fun getEvents() {
        repository.getEvents(
            onSuccess = { list ->
                events.value = list
                Log.d("___", list!![0].date)
            },
            onFailure = { errorMessage ->
                Log.d("___", errorMessage.message)
            }
        )
    }

    class FavoritesFactory(private var repository: ApiRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FavoritesViewModel(repository) as T
        }
    }
}