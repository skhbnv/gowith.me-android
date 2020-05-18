package com.iitu.gowithme.data.network.event_list

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.iitu.gowithme.data.models.response.EventResponse
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