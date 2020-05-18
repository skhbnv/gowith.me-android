package com.iitu.gowithme.data.network.user

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.iitu.gowithme.data.models.response.ProfileInfoResponse
import com.iitu.gowithme.data.models.response.ShortUserInfo
import com.iitu.gowithme.util.Result
import com.iitu.gowithme.util.apiCall
import kotlinx.coroutines.CoroutineScope
import okhttp3.ResponseBody

interface IUserProfileRepository {

    fun getUserList(listType: UserListType, scope: CoroutineScope) : LiveData<PagedList<ShortUserInfo>>

    suspend fun getUserProfileInfo(userId: Int) : Result<ProfileInfoResponse>
    suspend fun subscribeOnUser(userId: Int) : Result<ResponseBody>
    suspend fun unSubscribeFromUser(userId: Int) : Result<ResponseBody>
    suspend fun removeUser(eventId: Int, userId: Int) : Result<ResponseBody>
}

class UserProfileRepository(
    private val service: UserProfileService
) : IUserProfileRepository {

    override fun getUserList(listType: UserListType, scope: CoroutineScope): LiveData<PagedList<ShortUserInfo>> {
        val source = PagedKeyedUserProfileDataSource.Factory(listType, service, scope)
        return source.toLiveData(pageSize = 10)
    }

    override suspend fun getUserProfileInfo(userId: Int): Result<ProfileInfoResponse> = apiCall { service.getUserProfileInfo(userId) }

    override suspend fun subscribeOnUser(userId: Int): Result<ResponseBody> = apiCall { service.subscribeOnUser(userId) }

    override suspend fun unSubscribeFromUser(userId: Int): Result<ResponseBody> = apiCall { service.unSubscribeFromUser(userId) }

    override suspend fun removeUser(eventId: Int, userId: Int): Result<ResponseBody> = apiCall { service.removeUser(eventId, userId) }

}