package com.example.gowithme.data.models.response
import com.google.gson.annotations.SerializedName


data class CreateEventResponse(
    @SerializedName("categories")
    val categories: List<Int>,
    @SerializedName("description")
    val description: String,
    @SerializedName("end")
    val end: String,
    @SerializedName("images")
    val images: List<Any>,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("start")
    val start: String,
    @SerializedName("title")
    val title: String
)