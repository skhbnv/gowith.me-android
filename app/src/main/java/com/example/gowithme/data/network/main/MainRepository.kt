package com.example.gowithme.data.network.main

import android.content.SharedPreferences
import com.example.gowithme.util.PreferencesConst

interface IMainRepository {

    fun getAccessToken(): String

}

class MainRepository(
    private val pref: SharedPreferences
) : IMainRepository {

    override fun getAccessToken(): String =
        pref.getString(PreferencesConst.ACCESS_TOKEN, "") ?: ""

}