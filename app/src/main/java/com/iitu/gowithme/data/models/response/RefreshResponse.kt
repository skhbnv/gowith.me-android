package com.iitu.gowithme.data.models.response
import com.google.gson.annotations.SerializedName


data class RefreshResponse(
    @SerializedName("access")
    val access: String
)