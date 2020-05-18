package com.iitu.gowithme.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.iitu.gowithme.data.network.event.IEventRepository
import com.iitu.gowithme.data.network.event_list.EventListType
import com.iitu.gowithme.ui.home.model.NamedEventList
import com.iitu.gowithme.util.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel(var repository: IEventRepository) : ViewModel() {

    private val _events = MutableLiveData<List<NamedEventList>>()
    val eventsLD: LiveData<List<NamedEventList>> get() = _events

    fun getEvents() {
        viewModelScope.launch {
            Log.d("taaag", "launch2")

            val upComing = async { repository.getUpComingEvents() }
            val new = async { repository.getNewEvents() }
            val most = async { repository.getMostViewedEvents() }

            val special = async { repository.getSpecialEvents() }

            val list = ArrayList<NamedEventList>()
            when (val result = special.await()) {
                is Result.Success -> {
                    Log.d("taaag", "Success ${result.data}")
                    if (result.data.results.isNotEmpty()){
                        list.add(NamedEventList(EventListType.SPECIAL, "Специально для вас", result.data.results))
                    }
                }

                is Result.Error -> {
                    Log.d("taaag", "Error ${result.exception}")
                }
            }
            when (val result = most.await()) {
                is Result.Success -> {
                    Log.d("taaag", "Success ${result.data}")
                    list.add(NamedEventList(EventListType.MOST_VIEWED, "Популярные", result.data.results))
                }

                is Result.Error -> {
                    Log.d("taaag", "Error ${result.exception}")
                }
            }
            when (val result = upComing.await()) {
                is Result.Success -> {
                    Log.d("taaag", "Success ${result.data}")
                    list.add(NamedEventList(EventListType.UPCOMING, "Вот вот начнется", result.data.results))
                }

                is Result.Error -> {
                    Log.d("taaag", "Error ${result.exception}")
                }
            }
            when (val result = new.await()) {
                is Result.Success -> {
                    Log.d("taaag", "Success ${result.data}")
                    list.add(NamedEventList(EventListType.NEW, "Новые", result.data.results))
                }

                is Result.Error -> {
                    Log.d("taaag", "Error ${result.exception}")
                }
            }

            _events.value = list
        }
    }

}