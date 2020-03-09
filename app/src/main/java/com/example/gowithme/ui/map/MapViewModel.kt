package com.example.gowithme.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gowithme.network.ApiRepository

class MapViewModel(private var repository: ApiRepository): ViewModel() {



    class FavoritesFactory(private var repository: ApiRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MapViewModel(repository) as T
        }
    }
}