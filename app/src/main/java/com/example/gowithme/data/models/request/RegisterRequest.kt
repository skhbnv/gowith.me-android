package com.example.gowithme.data.models.request
import com.google.gson.annotations.SerializedName


data class RegisterRequest(
//    @SerializedName("email")
//    val email: String,
    @SerializedName("favorite_category")
    val favoriteCategory: List<Int>,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("email")
    val email: String
)