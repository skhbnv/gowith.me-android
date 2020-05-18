package com.iitu.gowithme.data.network.main

import android.content.SharedPreferences
import com.iitu.gowithme.data.models.response.ProfileInfoResponse
import com.iitu.gowithme.data.network.profile.ProfileService
import com.iitu.gowithme.util.PreferencesConst
import com.iitu.gowithme.util.Result
import com.iitu.gowithme.util.apiCall
import com.google.gson.Gson

interface IMainRepository {

    fun getAccessToken(): String
    fun removeTokens()
    fun saveMyInfo(info: ProfileInfoResponse)
    fun getMyInfoLocal() : ProfileInfoResponse?

    suspend fun getMyInfo(): Result<ProfileInfoResponse>

}

class MainRepository(
    private val pref: SharedPreferences,
    private val profileService: ProfileService
) : IMainRepository {
    override fun getMyInfoLocal(): ProfileInfoResponse? {
        val json = pref.getString(PreferencesConst.PROFILE_INFO, null)
        return if (json != null) Gson().fromJson(json, ProfileInfoResponse::class.java) else null
    }

    override fun saveMyInfo(info: ProfileInfoResponse) {
        pref.edit().putString(PreferencesConst.PROFILE_INFO, Gson().toJson(info)).apply()
    }



    override suspend fun getMyInfo(): Result<ProfileInfoResponse> = apiCall { profileService.getMyInfo() }


    override fun getAccessToken(): String =
        pref.getString(PreferencesConst.ACCESS_TOKEN, "") ?: ""

    override fun removeTokens() {
        pref.edit().putString(PreferencesConst.ACCESS_TOKEN, "").apply()
        pref.edit().putString(PreferencesConst.REFRESH_TOKEN, "").apply()
    }

}