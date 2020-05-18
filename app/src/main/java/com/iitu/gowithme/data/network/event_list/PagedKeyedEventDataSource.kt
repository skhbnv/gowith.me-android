package com.iitu.gowithme.data.network.event_list

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.iitu.gowithme.data.models.response.EventResponse
import com.iitu.gowithme.util.Result
import com.iitu.gowithme.util.apiCall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PagedKeyedEventDataSource(
    private val service: EventListService,
    private val scope: CoroutineScope,
    private val eventListType: EventListType,
    private val id: Int
) : PageKeyedDataSource<Int, EventResponse>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, EventResponse>
    ) {
        scope.launch {
            when (val result = getEventList(eventListType, 1, id)) {
                is Result.Success -> {
                    Log.d("taaag", "loadInitial Success")
                    callback.onResult(result.data.results, null, 2)
                }
                is Result.Error -> {
                    Log.d("taaag", "loadInitial Error ${result.exception}")
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, EventResponse>) {
        scope.launch {
            when (val result = getEventList(eventListType, params.key, id)) {
                is Result.Success -> {
                    Log.d("taaag", "loadAfter Success")
                    callback.onResult(result.data.results, params.key + 1)
                }
                is Result.Error -> {
                    Log.d("taaag", "loadAfter Error ${result.exception}")

                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, EventResponse>) {}

    private suspend fun getEventList(eventListType: EventListType, page: Int, id: Int) = when (eventListType) {
        EventListType.VIEWED_EVENTS -> apiCall { service.getViewEvents(page) }
        EventListType.MY_EVENTS -> apiCall { service.getMyEvents(page) }
        EventListType.SAVED_EVENTS -> apiCall { service.getSavedEvents(page) }

        EventListType.NEW -> apiCall { service.getEvents(page, "-created") }
        EventListType.MOST_VIEWED -> apiCall { service.getEvents(page, "-view_counter") }
        EventListType.SPECIAL -> apiCall { service.getSpecialEvents(page) }
        EventListType.UPCOMING -> apiCall { service.getEvents(page, "start") }
        EventListType.USER_EVENTS -> apiCall { service.getUserEvents(id, page) }
        EventListType.FRIENDS_EVENTS -> apiCall { service.getFollowingEvents(page) }
    }

    class EventDataSourceFactory(
        private val service: EventListService,
        private val scope: CoroutineScope,
        private val eventListType: EventListType,
        private val id: Int
    ): DataSource.Factory<Int, EventResponse>() {
        override fun create(): DataSource<Int, EventResponse> {
            return PagedKeyedEventDataSource(service, scope, eventListType, id)
        }
    }
}