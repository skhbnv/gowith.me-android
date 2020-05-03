package com.example.gowithme.data.models.response
import com.google.gson.annotations.SerializedName


data class MyInfoResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("events_created_count")
    val eventsCreatedCount: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("followers_count")
    val followersCount: Int,
    @SerializedName("following_count")
    val followingCount: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    val images: List<ImageResponse>,
    @SerializedName("last_login")
    val lastLogin: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("saved_events_count")
    val savedEventsCount: Int
) {
    val fullName get() = "$firstName $lastName"
}
