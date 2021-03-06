package com.iitu.gowithme.data.network.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserListType(
    val type: UserListTypeEnum,
    val id: Int = -1,
    val showCancel: Boolean = false
): Parcelable

enum class UserListTypeEnum{
    EVENT_SUBSCRIBERS,
    MY_FOLLOWERS,
    MY_FOLLOWING,
    USER_FOLLOWERS,
    USER_FOLLOWING
}