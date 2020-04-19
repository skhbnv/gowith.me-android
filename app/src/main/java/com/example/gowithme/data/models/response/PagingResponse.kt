package com.example.gowithme.data.models.response

import com.google.gson.annotations.SerializedName

data class PagingResponse<T>(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Any,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<T>
)