package com.example.gowithme.data.network.event_list

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.data.models.response.PagingResponse
import com.example.gowithme.util.Result
import com.example.gowithme.util.apiCall
import kotlinx.coroutines.CoroutineScope

interface IEventListRepository {
    fun getEventList(scope: CoroutineScope, eventListType: EventListType, id: Int) : LiveData<PagedList<EventResponse>>
}

class EventListRepository(
    private val service: EventListService
) : IEventListRepository {

    override fun getEventList(scope: CoroutineScope, eventListType: EventListType, id: Int): LiveData<PagedList<EventResponse>> {
        val source = PagedKeyedEventDataSource.EventDataSourceFactory(service, scope, eventListType, id)
        return source.toLiveData(pageSize = 10)
    }
}