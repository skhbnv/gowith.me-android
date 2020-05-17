package com.example.gowithme.ui.event_page

import android.util.Log
import androidx.lifecycle.*
import com.example.gowithme.data.models.request.CommentRequest
import com.example.gowithme.data.models.response.CommentResponse
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.data.network.event.IEventRepository
import com.example.gowithme.util.Result
import kotlinx.coroutines.launch
import java.lang.Exception

class EventPageViewModel(var repository: IEventRepository) : ViewModel(){

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _eventDetailsUI = MutableLiveData<EventPageUI>()
    val eventDetailsUI: LiveData<EventPageUI> get() = _eventDetailsUI

    private val _comments = MutableLiveData<List<CommentResponse>>()
    val comment: LiveData<List<CommentResponse>> get() = _comments

    fun getEventDetails(id: Int) {
        Log.d("taaag", "getEventDetails")
        _loading.value = true
        viewModelScope.launch {
            when(val result = repository.getEventDetails(id)) {
                is Result.Success -> {
                    Log.d("taaag", "getEventDetails Success ${result.data}")
                    _eventDetailsUI.value = EventPageUI.EventLoaded(result.data)
                    _loading.value = false
                }
                is Result.Error -> {
                    Log.d("taaag", "getEventDetails ${result.exception}")
                    _eventDetailsUI.value = EventPageUI.EventLoadError(result.exception)
                }
            }
            _loading.value = false
        }
    }

    fun getComments(id: Int) {
        viewModelScope.launch {
            when(val result = repository.getEventComments(id)) {
                is Result.Success -> {
                    _eventDetailsUI.value = EventPageUI.EventComments(result.data)
                }
                is Result.Error -> {
                    _eventDetailsUI.value = EventPageUI.EventLoadError(result.exception)
                }
            }
        }
    }

    fun postComment(id: Int, comment: String) {
        _loading.value = true
        viewModelScope.launch {
            val request = CommentRequest(comment, id)
            when(val result = repository.postComment(request)) {
                is Result.Success -> {
                    _eventDetailsUI.value = EventPageUI.PostCommentSuccess(result.data)
                }
                is Result.Error -> {
                    _eventDetailsUI.value = EventPageUI.EventLoadError(result.exception)
                }
            }
            _loading.value = false
        }
    }

    fun save(id: Int) {
        viewModelScope.launch {
            when(val result = repository.saveEvent(id)) {
                is Result.Success -> {

                }
                is Result.Error -> {

                }
            }
        }
    }

    fun unSave(id: Int) {
        viewModelScope.launch {
            when(val result = repository.unSaveEvent(id)) {
                is Result.Success -> {

                }
                is Result.Error -> {

                }
            }
        }
    }

    fun subscribeOnEvent(id: Int) {
        viewModelScope.launch {
            when(val result = repository.subscribeOnEvent(id)) {
                is Result.Success -> {
                    _eventDetailsUI.value = EventPageUI.OnEventSubscribeSuccess
                }
                is Result.Error -> {
                    _eventDetailsUI.value = EventPageUI.OnEventSubscribeError(result.exception)
                }
            }
        }
    }
}

sealed class EventPageUI {

    data class EventLoaded(val event: EventResponse) : EventPageUI()
    data class EventLoadError(val exception: Exception) : EventPageUI()

    data class EventComments(val comments: List<CommentResponse>) : EventPageUI()
    data class PostCommentSuccess(val comment: CommentResponse) : EventPageUI()

    object OnEventSubscribeSuccess : EventPageUI()
    data class OnEventSubscribeError(val exception: Exception) : EventPageUI()

}