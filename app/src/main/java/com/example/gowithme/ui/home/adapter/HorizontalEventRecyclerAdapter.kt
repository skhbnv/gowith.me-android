package com.example.gowithme.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gowithme.BuildConfig
import com.example.gowithme.R
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.util.inflate
import kotlinx.android.synthetic.main.nearby_card.view.*

class HorizontalEventRecyclerAdapter() : RecyclerView.Adapter<HorizontalEventRecyclerAdapter.HorizontalEventViewHolder>() {

    private val events: ArrayList<EventResponse> = ArrayList()

    fun setEvents(data: List<EventResponse>) {
        events.clear()
        events.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalEventViewHolder =
        HorizontalEventViewHolder(parent.inflate(R.layout.nearby_card))

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: HorizontalEventViewHolder, position: Int) {
        holder.bindView(events[position])
    }

    inner class HorizontalEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val glide = Glide.with(itemView.context)

        fun bindView(event: EventResponse) {
            with(itemView) {
                title.text = event.title
                description.text = event.description
                event.images.firstOrNull()?.image?.let {
                    glide.load(BuildConfig.BASE_URL + it.substring(1)).into(eventImage)
                }
            }
        }
    }
}