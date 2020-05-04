package com.example.gowithme.data.network.main

import android.content.SharedPreferences
import com.example.gowithme.data.models.response.MyInfoResponse
import com.example.gowithme.data.network.profile.ProfileService
import com.example.gowithme.util.PreferencesConst
import com.example.gowithme.util.Result
import com.example.gowithme.util.apiCall
import com.google.gson.Gson

interface IMainRepository {

    fun getAccessToken(): String
    fun removeTokens()
    fun saveMyInfo(info: MyInfoResponse)
    fun getMyInfoLocal() : MyInfoResponse?

    suspend fun getMyInfo(): Result<MyInfoResponse>

}

class MainRepository(
    private val pref: SharedPreferences,
    private val profileService: ProfileService
) : IMainRepository {
    override fun getMyInfoLocal(): MyInfoResponse? {
        val json = pref.getString(PreferencesConst.PROFILE_INFO, null)
        return if (json != null) Gson().fromJson(json, MyInfoResponse::class.java) else null
    }

    override fun saveMyInfo(info: MyInfoResponse) {
        pref.edit().putString(PreferencesConst.PROFILE_INFO, Gson().toJson(info)).apply()
    }



    override suspend fun getMyInfo(): Result<MyInfoResponse> = apiCall { profileService.getMyInfo() }


    override fun getAccessToken(): String =
        pref.getString(PreferencesConst.ACCESS_TOKEN, "") ?: ""

    override fun removeTokens() {
        pref.edit().putString(PreferencesConst.ACCESS_TOKEN, "").apply()
        pref.edit().putString(PreferencesConst.REFRESH_TOKEN, "").apply()
    }

}