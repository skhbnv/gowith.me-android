package com.example.gowithme.data.models.response
import com.google.gson.annotations.SerializedName


data class CheckPhoneResponse(
    @SerializedName("content")
    val content: String
)