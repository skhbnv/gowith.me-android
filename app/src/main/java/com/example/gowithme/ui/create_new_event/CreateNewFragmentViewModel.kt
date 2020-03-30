package com.example.gowithme.ui.create_new_event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gowithme.data.network.ApiRepository
import com.example.gowithme.responses.GeneralEvents
import java.io.InputStream

class CreateNewFragmentViewModel(private var repository: ApiRepository) : ViewModel() {

    val events = MutableLiveData<List<GeneralEvents>>()
    fun loadJsonFromAsset(inst: InputStream) = repository.loadJsonFromAsset(inst)

    fun getEvents() {
//        repository.getEvents(
//            onSuccess = { list ->
//                events.data = list
//                d("___", list!![0].date)
//            },
//            onFailure = { errorMessage ->
//                d("___", errorMessage.message)
//            }
//        )
    }

    class DashboardFactory(private var repository: ApiRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CreateNewFragmentViewModel(repository) as T
        }
    }
}