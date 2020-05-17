package com.example.gowithme.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gowithme.BuildConfig
import com.example.gowithme.R
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.util.format
import com.example.gowithme.util.inflate
import com.example.gowithme.util.toDate
import kotlinx.android.synthetic.main.nearby_card.view.*

class HorizontalEventRecyclerAdapter(
    private val events: List<EventResponse>
) : RecyclerView.Adapter<HorizontalEventRecyclerAdapter.HorizontalEventViewHolder>() {

    private var onEventClickedListener: ((Int) -> Unit)? = null

    fun setOnEventClickedListener(listener: (Int) -> Unit) {
        onEventClickedListener = listener
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
                event.images.firstOrNull()?.image?.let { glide.load(BuildConfig.BASE_URL + it.substring(1)).into(full_size_poster) }
                title.text = event.title
                message.text = event.description
                this.setOnClickListener {
                    onEventClickedListener?.invoke(event.id)
                }
                date_time.text = context?.getString(R.string.text_start_date, event.start.toDate().format("dd MMM, hh:mm"))
                viewCounter.text = event.viewCounter.toString()
            }
        }
    }
}