package com.example.gowithme.data.models.request

data class ConfirmPhoneRequest (
    val phone: String,
    val code: String
)