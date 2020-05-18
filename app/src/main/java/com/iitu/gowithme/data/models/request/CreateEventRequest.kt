package com.iitu.gowithme.data.models.request
import com.google.gson.annotations.SerializedName


data class CreateEventRequest(
    @SerializedName("categories")
    val categories: List<Int>,
    @SerializedName("description")
    val description: String,
    @SerializedName("end")
    val end: String,
    @SerializedName("images")
    val images: List<Any>,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("price")
    val price: String,
    @SerializedName("start")
    val start: String,
    @SerializedName("title")
    val title: String
)