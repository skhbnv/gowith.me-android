package com.example.gowithme.responses

data class ProfileInfo(
    val user: User,
    val city_name: String,
    val events_created: Int,
    val events_visited: Int,
    val id: Int,
    val last_activity: List<GeneralEvents>,
    val location: Location
)