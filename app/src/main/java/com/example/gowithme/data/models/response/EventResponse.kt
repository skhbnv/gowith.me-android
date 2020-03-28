package com.example.gowithme.data.models.response
import com.google.gson.annotations.SerializedName


data class EventResponse(
    @SerializedName("author")
    val author: Int,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("description")
    val description: String,
    @SerializedName("end")
    val end: String,
    @SerializedName("id")
    val id: Int,
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
) {
    data class Category(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )
}