package com.iitu.gowithme.ui.user_profile.user_list.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iitu.gowithme.BuildConfig
import com.iitu.gowithme.R
import com.iitu.gowithme.data.models.response.ShortUserInfo
import com.iitu.gowithme.data.network.user.UserListType
import com.iitu.gowithme.data.network.user.UserListTypeEnum
import com.iitu.gowithme.databinding.ItemShortUserProfileBinding
import com.iitu.gowithme.util.inflateBinding

class UserListPagedAdapter(
    private val listType: UserListType
) : PagedListAdapter<ShortUserInfo, UserListPagedAdapter.ViewHolder>(USER_COMPARATOR) {

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<ShortUserInfo>() {
            override fun areItemsTheSame(
                oldItem: ShortUserInfo,
                newItem: ShortUserInfo
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ShortUserInfo,
                newItem: ShortUserInfo
            ): Boolean = oldItem.firstName == newItem.firstName &&
                    oldItem.lastName == newItem.lastName

        }
    }

    var onProfileClicked : ((Int) -> Unit)? = null
    var onRemoveClicked : ((Int) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemShortUserProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val glide = Glide.with(binding.root.context)

        @SuppressLint("SetTextI18n")
        fun bind(userInfo: ShortUserInfo?) {
            Log.d("taaag", "UserListPagedAdapter bind")
            with(binding) {
                if (userInfo != null) {
                    name.text = "${userInfo.firstName} ${userInfo.lastName}"
                    userInfo.image?.image?.let { glide.load(BuildConfig.BASE_URL + it.substring(1)).into(avatar) }
                    root.setOnClickListener {
                        onProfileClicked?.invoke(userInfo.id)
                    }
                    if (listType.showCancel && listType.type == UserListTypeEnum.EVENT_SUBSCRIBERS) {
                        remove.visibility = View.VISIBLE
                        remove.setOnClickListener {
                            onRemoveClicked?.invoke(userInfo.id)
                        }
                    } else {
                        remove.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflateBinding(R.layout.item_short_user_profile))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}