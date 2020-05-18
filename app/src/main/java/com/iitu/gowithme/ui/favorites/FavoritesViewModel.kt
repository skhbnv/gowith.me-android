package com.iitu.gowithme.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iitu.gowithme.data.network.ApiRepository
import com.iitu.gowithme.responses.GeneralEvents

class FavoritesViewModel(private var repository: ApiRepository): ViewModel() {
    val events = MutableLiveData<List<GeneralEvents>>()

    fun getEvents() {
//        repository.getEvents(
//            onSuccess = { list ->
//                events.data = list
//                Log.d("___", list!![0].date)
//            },
//            onFailure = { errorMessage ->
//                Log.d("___", errorMessage.message)
//            }
//        )
    }

    class FavoritesFactory(private var repository: ApiRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FavoritesViewModel(repository) as T
        }
    }
}