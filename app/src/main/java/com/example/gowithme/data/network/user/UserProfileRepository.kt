package com.example.gowithme.data.network.user

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.gowithme.data.models.response.ShortUserInfo
import kotlinx.coroutines.CoroutineScope

interface IUserProfileRepository {

    fun getEventSubscribersList(eventId: Int, scope: CoroutineScope) : LiveData<PagedList<ShortUserInfo>>

}

class UserProfileRepository(
    private val service: UserProfileService
) : IUserProfileRepository {

    override fun getEventSubscribersList(eventId: Int, scope: CoroutineScope): LiveData<PagedList<ShortUserInfo>> {
        val source = PagedKeyedUserProfileDataSource.Factory(eventId, service, scope)
        return source.toLiveData(pageSize = 10)
    }

}