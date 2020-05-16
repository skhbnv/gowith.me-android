package com.example.gowithme.data.models.response
import com.google.gson.annotations.SerializedName


data class CommentResponse(
    @SerializedName("author")
    val author: Author,
    @SerializedName("children")
    val children: List<CommentResponse>,
    @SerializedName("content")
    val content: String,
    @SerializedName("created")
    val created: String
) {
    data class Author(
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("last_name")
        val lastName: String
    )
}