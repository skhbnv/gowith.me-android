package com.example.gowithme.data.models.response

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("description")
    val description: String?,
    @SerializedName("image")
    val image: String
)