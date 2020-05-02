package com.example.gowithme.data.models.response
import com.google.gson.annotations.SerializedName


data class MyInfoResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_login")
    val lastLogin: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("phone")
    val phone: String
) {

    val fullName get() = "$firstName $lastName"

}