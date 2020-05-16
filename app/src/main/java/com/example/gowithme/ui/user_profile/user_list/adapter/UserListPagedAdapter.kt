package com.example.gowithme.ui.user_profile.user_list.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gowithme.BuildConfig
import com.example.gowithme.R
import com.example.gowithme.data.models.response.ShortUserInfo
import com.example.gowithme.databinding.ItemShortUserProfileBinding
import com.example.gowithme.util.inflateBinding

class UserListPagedAdapter :
    PagedListAdapter<ShortUserInfo, UserListPagedAdapter.ViewHolder>(USER_COMPARATOR) {

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