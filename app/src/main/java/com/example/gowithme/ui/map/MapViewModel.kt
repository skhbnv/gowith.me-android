package com.example.gowithme.ui.map

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gowithme.data.network.ApiRepository
import com.example.gowithme.responses.GeneralEvents
import java.io.InputStream

class MapViewModel(private var repository: ApiRepository): ViewModel() {
    var events = MutableLiveData<ArrayList<GeneralEvents>>()
    var selectedGeneralEvents = MutableLiveData<GeneralEvents>()
    var markerInfoVisibility = ObservableInt(View.GONE)
    var markerListVisibility = ObservableInt(View.GONE)

    fun loadJsonFromAsset(inst: InputStream) = repository.loadJsonFromAsset(inst)

    class MapFactory(private var repository: ApiRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MapViewModel(repository) as T
        }
    }
}