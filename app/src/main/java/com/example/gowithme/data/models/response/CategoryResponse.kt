package com.example.gowithme.data.models.response
import com.google.gson.annotations.SerializedName


data class CategoryResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    var isChecked: Boolean = false
)