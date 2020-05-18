package com.iitu.gowithme.data.models.response

import com.google.gson.annotations.SerializedName

data class ShortUserInfo(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: ImageResponse?,
    @SerializedName("last_name")
    val lastName: String
)