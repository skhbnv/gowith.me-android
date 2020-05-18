package com.iitu.gowithme.data.network.user

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.iitu.gowithme.data.models.response.ShortUserInfo
import com.iitu.gowithme.util.Result
import com.iitu.gowithme.util.apiCall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PagedKeyedUserProfileDataSource(
    private val listType: UserListType,
    private val service: UserProfileService,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, ShortUserInfo>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ShortUserInfo>
    ) {
        scope.launch { 
            when(val result = getEventSubscribers(1)) {
                is Result.Success -> {
                    callback.onResult(result.data.results, null, 2)
                }
                is Result.Error -> {
                    Log.d("taaag", "loadInitial Error ${result.exception}")
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ShortUserInfo>) {
        scope.launch {
            when(val result = getEventSubscribers(params.key)) {
                is Result.Success -> {
                    callback.onResult(result.data.results, params.key + 1)
                }
                is Result.Error -> {
                    Log.d("taaag", "loadAfter Error ${result.exception}")
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ShortUserInfo>) {}

    private suspend fun getEventSubscribers(page: Int) = when(listType.type) {
        UserListTypeEnum.EVENT_SUBSCRIBERS -> apiCall { service.getEventSubscribers(listType.id, page) }
        UserListTypeEnum.MY_FOLLOWERS -> apiCall { service.getMyFollowers(page) }
        UserListTypeEnum.MY_FOLLOWING -> apiCall { service.getMyFollowing(page) }
        UserListTypeEnum.USER_FOLLOWERS -> apiCall { service.getUserFollowers(listType.id, page) }
        UserListTypeEnum.USER_FOLLOWING -> apiCall { service.getUserFollowing(listType.id, page) }
    }



    class Factory(
        private val listType: UserListType,
        private val service: UserProfileService,
        private val scope: CoroutineScope
    ) : DataSource.Factory<Int, ShortUserInfo>() {
        override fun create(): DataSource<Int, ShortUserInfo> {
            return PagedKeyedUserProfileDataSource(listType, service, scope)
        }
    }
}