package com.example.gowithme.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.R
import com.example.gowithme.responses.Event
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.event_card.view.*

class EventsAdapter(
    var _context: Context,
    var _eventsList: ArrayList<Event> = ArrayList(),
    var _onClick: (Event)->Unit
    ): RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): EventsViewHolder {
        return EventsViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.event_card, parent, false))
    }

    override fun getItemCount(): Int = _eventsList.size

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind()
    }

    fun initTheList(listOfEvents: List<Event>){
        _eventsList.clear()
        _eventsList.addAll(listOfEvents)
        notifyDataSetChanged()
    }
    inner class EventsViewHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener{
        private var title = v.title
        private var poster = v.back_poster
        private var dateTime = v.date_time
        private var price = v.price

        fun bind(){
            val currentEvent = _eventsList[adapterPosition]
            title.text = currentEvent.title
            Picasso.get().load(currentEvent.images.poster_url).into(poster)
            dateTime.text = currentEvent.date
            price.text = currentEvent.price
        }
        override fun onClick(p0: View?) {
            _onClick.invoke(_eventsList[adapterPosition])
        }
    }
}