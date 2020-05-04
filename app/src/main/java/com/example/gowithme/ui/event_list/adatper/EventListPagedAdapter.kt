package com.example.gowithme.ui.event_list.adatper

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gowithme.BuildConfig
import com.example.gowithme.R
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.databinding.ItemEventListBinding
import com.example.gowithme.util.inflateBinding
import com.example.gowithme.util.tenge

class EventListPagedAdapter :
    PagedListAdapter<EventResponse, EventListPagedAdapter.EventViewHolder>(EVENT_COMPARATOR) {

    private var onEventClickedListener: ((Int) -> Unit)? = null

    fun setOnEventClickedListener(listener: (Int) -> Unit) {
        onEventClickedListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder =
        EventViewHolder(parent.inflateBinding(R.layout.item_event_list))

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EventViewHolder(val binding: ItemEventListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val glide = Glide.with(binding.root.context)
        @SuppressLint("SetTextI18n")
        fun bind(item: EventResponse?) {
            with(binding) {
                if (item != null) {
                    title.text = item.title + item.id
                    description.text = item.description
                    glide.load(BuildConfig.BASE_URL + item.images.firstOrNull()?.image?.substring(1))
                        .into(eventImage)
                    glide.load(BuildConfig.BASE_URL + item.author.image?.image?.substring(1))
                        .into(authorImage)
                    authorName.text = "${item.author.firstName} ${item.author.lastName}"
                    viewCount.text = item.viewCounter.toString()
                    startDate.text = root.context.getString(R.string.text_start_date, item.start)
                    price.text = if(item.price == 0) root.context.getString(R.string.text_free) else {
                        root.context.getString(R.string.text_price, item.price.toString().tenge())
                    }
                    root.setOnClickListener {
                        onEventClickedListener?.invoke(item.id)
                    }
                }
            }
        }
    }

    companion object {
        private val EVENT_COMPARATOR = object : DiffUtil.ItemCallback<EventResponse>() {
            override fun areItemsTheSame(
                oldItem: EventResponse,
                newItem: EventResponse
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: EventResponse,
                newItem: EventResponse
            ): Boolean = oldItem.title == newItem.title &&
                    oldItem.description == newItem.description
        }
    }
}