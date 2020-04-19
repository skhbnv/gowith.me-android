package com.example.gowithme.data.network.profile

import com.example.gowithme.data.models.response.MyInfoResponse
import com.example.gowithme.util.Result
import com.example.gowithme.util.apiCall

interface IProfileRepository {

    suspend fun getMyInfo(): Result<MyInfoResponse>

}

class ProfileRepository(
    private val service: ProfileService
) : IProfileRepository {

    override suspend fun getMyInfo(): Result<MyInfoResponse> = apiCall { service.getMyInfo() }

}