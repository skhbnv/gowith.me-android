package com.example.gowithme.data.network.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserListType(
    val type: UserListTypeEnum,
    val id: Int = -1
): Parcelable

enum class UserListTypeEnum{
    EVENT_SUBSCRIBERS,
    MY_FOLLOWERS,
    MY_FOLLOWING,
    USER_FOLLOWERS,
    USER_FOLLOWING
}