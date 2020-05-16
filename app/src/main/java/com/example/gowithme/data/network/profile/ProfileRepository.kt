package com.example.gowithme.data.network.profile

import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.data.models.response.ProfileInfoResponse
import com.example.gowithme.data.models.response.PagingResponse
import com.example.gowithme.util.Result
import com.example.gowithme.util.apiCall

interface IProfileRepository {

    suspend fun getMyInfo(): Result<ProfileInfoResponse>
    suspend fun getViewEvents(page: Int = 1) : Result<PagingResponse<EventResponse>>

}

class ProfileRepository(
    private val service: ProfileService
) : IProfileRepository {
    override suspend fun getMyInfo(): Result<ProfileInfoResponse> = apiCall { service.getMyInfo() }

    override suspend fun getViewEvents(page: Int): Result<PagingResponse<EventResponse>> = apiCall { service.getViewEvents(page) }

}
