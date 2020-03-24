package com.example.gowithme.ui.home

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gowithme.network.ApiRepository
import com.example.gowithme.responses.GeneralEvents
import java.io.InputStream

class HomeViewModel(var repository: ApiRepository) : ViewModel() {

    val events = MutableLiveData<List<GeneralEvents>>()
    fun loadJsonFromAsset(inst: InputStream) = repository.loadJsonFromAsset(inst)


    class HomeFactory(var repo : ApiRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(repo) as T
        }
    }
}