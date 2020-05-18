package com.iitu.gowithme.data.models.response
import com.google.gson.annotations.SerializedName


data class ProfileInfoResponse(
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
    val savedEventsCount: Int,
    @SerializedName("telegram_username")
    val telegramUsername: String,
    @SerializedName("is_me_follower")
    val isMeFollower: Boolean
) {
    val fullName get() = "$firstName $lastName"
}
