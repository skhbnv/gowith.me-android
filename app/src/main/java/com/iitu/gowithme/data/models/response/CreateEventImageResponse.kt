package com.iitu.gowithme.data.models.response
import com.google.gson.annotations.SerializedName


data class CreateEventImageResponse(
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String
)