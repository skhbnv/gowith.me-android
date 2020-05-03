package com.example.gowithme.data.network.event_list

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.util.Result
import com.example.gowithme.util.apiCall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PagedKeyedEventDataSource(
    private val service: EventListService,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, EventResponse>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, EventResponse>
    ) {
        scope.launch {
            when (val result = apiCall { service.getViewEvents(1) }) {
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
            when (val result = apiCall { service.getViewEvents(params.key) }) {
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

    class EventDataSourceFactory(
        private val service: EventListService,
        private val scope: CoroutineScope
    ): DataSource.Factory<Int, EventResponse>() {
        override fun create(): DataSource<Int, EventResponse> {
            return PagedKeyedEventDataSource(service, scope)
        }
    }
}