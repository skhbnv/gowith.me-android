package com.iitu.gowithme.data.models.response
import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("access")
    val access: String,
    @SerializedName("refresh")
    val refresh: String
)