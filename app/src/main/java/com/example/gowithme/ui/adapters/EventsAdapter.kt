package com.example.gowithme.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.R
import com.example.gowithme.responses.GeneralEvents
import com.example.gowithme.util.RecyclerLayoutsType.BRIEF_INFO
import com.example.gowithme.util.RecyclerLayoutsType.FULL_SIZE
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_card.view.*
import kotlinx.android.synthetic.main.event_card.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EventsAdapter(
    var _context: Context,
    var _eventsList: ArrayList<GeneralEvents> = ArrayList(),
    var _onClick: (GeneralEvents) -> Unit,
    var layoutType: Int

) : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): EventsViewHolder {
        return when (layoutType){
            FULL_SIZE -> {
                EventsViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.event_card, parent, false))
            }
            BRIEF_INFO -> {
                EventsViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.brief_event_info, parent, false))
            }
            else -> {
                EventsViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.event_card, parent, false))
            }
        }
    }

    override fun getItemCount(): Int = _eventsList.size

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind()
    }

    fun initTheList(listOfEvents: List<GeneralEvents>) {
        _eventsList.clear()
        _eventsList.addAll(listOfEvents)
        notifyDataSetChanged()
    }

    inner class EventsViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var title = v.title
        private var poster = v.back_poster
        private var dateTime = v.date_time
        private var price = v.price
        private var views = v.views
        private var message = v.message
        private var whenTime = v.when_time
        private var avatar = v.avatar_img
        private var name = v.author_name
        private var likeIndicator = v.like_indicator

        private var llCategories = v.ll_categories

        fun bind() {
            val currentEvent = _eventsList[adapterPosition]
            val dt = getNeededDateTime(currentEvent.date)
            title?.text = currentEvent.title
            poster?.let {
                Picasso.get().load(currentEvent.poster_url).fit().centerCrop().into(it)
            }
            val authorName = "${currentEvent?.author?.first_name} ${currentEvent?.author?.last_name}"
            name?.text = authorName
            dateTime?.text = dt
            message?.text = currentEvent.message
            whenTime?.text = currentEvent.countDownTime
            avatar?.let{
                Picasso.get().load(currentEvent.author.avatar_url).fit().centerCrop().into(it)
            }
            price?.text = currentEvent.price
            views?.text = currentEvent.views
            itemView.setOnClickListener(this)
//            currentEvent.category.forEach{ c ->
//                val categoryCard = LayoutInflater.from(_context)
//                    .inflate(R.layout.category_card, null, false)
//                categoryCard.category_text.text = c
//                llCategories?.addView(categoryCard)
//            }
        }

        @SuppressLint("SimpleDateFormat")
        fun getNeededDateTime(source: String): String {
//            2020-01-15T18:00:00Z
            val originalFormat: DateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
            val targetFormat: DateFormat = SimpleDateFormat("dd MMM HH:mm")
            val date = originalFormat.parse(source)
            return targetFormat.format(date!!)
        }

        override fun onClick(p0: View?) {
            _onClick.invoke(_eventsList[adapterPosition])
        }
    }
}