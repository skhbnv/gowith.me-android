package com.example.gowithme.data.network.user

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.gowithme.data.models.response.ProfileInfoResponse
import com.example.gowithme.data.models.response.ShortUserInfo
import com.example.gowithme.util.Result
import com.example.gowithme.util.apiCall
import kotlinx.coroutines.CoroutineScope

interface IUserProfileRepository {

    fun getEventSubscribersList(eventId: Int, scope: CoroutineScope) : LiveData<PagedList<ShortUserInfo>>

    suspend fun getUserProfileInfo(userId: Int) : Result<ProfileInfoResponse>
}

class UserProfileRepository(
    private val service: UserProfileService
) : IUserProfileRepository {

    override fun getEventSubscribersList(eventId: Int, scope: CoroutineScope): LiveData<PagedList<ShortUserInfo>> {
        val source = PagedKeyedUserProfileDataSource.Factory(eventId, service, scope)
        return source.toLiveData(pageSize = 10)
    }

    override suspend fun getUserProfileInfo(userId: Int): Result<ProfileInfoResponse> = apiCall { service.getUserProfileInfo(userId) }

}