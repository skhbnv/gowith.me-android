package com.example.gowithme.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gowithme.network.ApiRepository
import com.example.gowithme.ui.dashboard.DashboardViewModel

class FavoritesViewModel(private var repository: ApiRepository): ViewModel() {



    class FavoritesFactory(private var repository: ApiRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FavoritesViewModel(repository) as T
        }
    }
}