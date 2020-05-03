package com.example.gowithme.data.models.response
import com.google.gson.annotations.SerializedName


data class EventResponse(
    @SerializedName("author")
    val author: Author,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("description")
    val description: String,
    @SerializedName("end")
    val end: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    val images: List<ImageResponse>,
    @SerializedName("is_saved")
    val isSaved: Boolean,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("start")
    val start: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("view_counter")
    val viewCounter: Int
) {
    data class Author(
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: ImageResponse?,
        @SerializedName("last_name")
        val lastName: String
    )

    data class Category(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )
}