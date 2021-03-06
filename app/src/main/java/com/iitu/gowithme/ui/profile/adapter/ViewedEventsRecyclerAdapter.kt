package com.iitu.gowithme.ui.profile.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iitu.gowithme.BuildConfig
import com.iitu.gowithme.R
import com.iitu.gowithme.data.models.response.EventResponse
import com.iitu.gowithme.util.inflate
import kotlinx.android.synthetic.main.item_viewed_event.view.*

class ViewedEventsRecyclerAdapter : RecyclerView.Adapter<ViewedEventsRecyclerAdapter.ViewedEventsViewHolder>() {

    private val eventList = ArrayList<EventResponse>()
    var onItemClicked: ((Int) -> Unit)? = null

    fun setEventList(data: List<EventResponse>) {
        eventList.clear()
        eventList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewedEventsViewHolder =
        ViewedEventsViewHolder(parent.inflate(R.layout.item_viewed_event))

    override fun getItemCount(): Int = eventList.size

    override fun onBindViewHolder(holder: ViewedEventsViewHolder, position: Int) {
        holder.bind(eventList[position])
    }


    inner class ViewedEventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val glide = Glide.with(itemView.context)

        @SuppressLint("SetTextI18n")
        fun bind(event: EventResponse) {
            with(itemView) {
                event.images.firstOrNull()?.image?.let {
                    glide.load(BuildConfig.BASE_URL + it.substring(1)).into(eventImage)
                }
                event.author.image?.image?.let {
                    glide.load(BuildConfig.BASE_URL + it.substring(1)).into(authorImage)
                }
                authorName.text = "${event.author.firstName} ${event.author.lastName} ${event.id}"
                setOnClickListener {
                    onItemClicked?.invoke(event.id)
                }
            }
        }
    }
}