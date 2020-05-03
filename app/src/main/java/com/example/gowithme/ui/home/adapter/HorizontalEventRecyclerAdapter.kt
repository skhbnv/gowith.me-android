package com.example.gowithme.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.R
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.util.inflate
import kotlinx.android.synthetic.main.event_card.view.*

class HorizontalEventRecyclerAdapter(
    private val events: List<EventResponse>
) : RecyclerView.Adapter<HorizontalEventRecyclerAdapter.HorizontalEventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalEventViewHolder =
        HorizontalEventViewHolder(parent.inflate(R.layout.nearby_card))

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: HorizontalEventViewHolder, position: Int) {
        holder.bindView(events[position])
    }

    inner class HorizontalEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(event: EventResponse) {
            with(itemView) {
                title.text = event.title
                message.text = event.description
            }
        }
    }
}