package com.example.gowithme.data.models.response
import com.google.gson.annotations.SerializedName


data class EventImageResponse(
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String
)