package com.example.gowithme.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gowithme.data.network.ApiRepository
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.responses.ProfileInfo
import com.example.gowithme.ui.adapters.EventsAdapter
import java.io.InputStream

class ProfileViewModel(var repository: ApiRepository) : ViewModel() {

    var profileInfo = MutableLiveData<ProfileInfo>()
    var lastActivityAdapter = MutableLiveData<RecyclerView.Adapter<EventsAdapter.EventsViewHolder>>()

    fun loadJsonFromAsset(inst: InputStream) = repository.loadJsonFromAsset(inst)

    class ProfileFactory(private var repository: ApiRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileViewModel(repository) as T
        }
    }
}