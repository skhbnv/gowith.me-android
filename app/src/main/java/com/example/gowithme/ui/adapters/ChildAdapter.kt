package com.example.gowithme.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.R
import com.example.gowithme.responses.GeneralEvents
import com.example.gowithme.util.RecyclerLayoutsType.COMING_SOON
import com.example.gowithme.util.RecyclerLayoutsType.NEARBY_EVENTS
import com.example.gowithme.util.RecyclerLayoutsType.POSTERS
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.coming_soon_card.view.*

import kotlinx.android.synthetic.main.nearby_card.view.*
import kotlinx.android.synthetic.main.nearby_card.view.date_time
import kotlinx.android.synthetic.main.nearby_card.view.message
import kotlinx.android.synthetic.main.nearby_card.view.title
import kotlinx.android.synthetic.main.nearby_card.view.will_come
import kotlinx.android.synthetic.main.poster_card.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ChildAdapter(private val children: List<GeneralEvents>,
                   private val layoutType: Int,
                   var onClick: (GeneralEvents) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        var v = View(parent.context)

        when (layoutType) {
            NEARBY_EVENTS -> {
                v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.nearby_card, parent, false)

                return NearbyViewHolder(v)
            }
            POSTERS -> {
                v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.poster_card, parent, false)
                v.poster.visibility = View.VISIBLE
                return PosterViewHolder(v)
            }
            COMING_SOON -> {
                v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.coming_soon_card, parent, false)
                return ComingSoonViewHolder(v)
            }
            else -> {
                return NearbyViewHolder(v)
            }
        }
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val child = children[position]

        when (holder) {
            is NearbyViewHolder -> {
                holder.bind(child)
            }
            is PosterViewHolder -> {
                holder.bind(child)
            }
            is ComingSoonViewHolder -> {
                holder.bind(child)
            }
        }
    }

    inner class NearbyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val title: TextView? = itemView.title
        private val message: TextView? = itemView.message
        private val dateTime: TextView? = itemView.date_time
        private val willCome: TextView? = itemView.will_come
        private val fullSizePoster: ImageView? = itemView.full_size_poster

        fun bind(child: GeneralEvents) {
            itemView.setOnClickListener {
                onClick.invoke(child)
            }

            fullSizePoster?.visibility = View.VISIBLE
            fullSizePoster?.let {
                Picasso.get().load(child.poster_url).fit().centerCrop().into(it)
            }
            title?.text = child.title
            dateTime?.text = getNeededDateTime(child.date)
            willCome?.text = child.subscribers.toString()
            message?.text = child.message
        }
    }

    inner class ComingSoonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView? = itemView.title
        private val timeCountdown: TextView? = itemView.time_countdown
        private val backPoster: ImageView? = itemView.back_poster

        fun bind(child: GeneralEvents) {
            itemView.setOnClickListener {
                onClick.invoke(child)
            }

            backPoster?.let {
                Picasso.get().load(child.poster_url).fit().centerCrop().into(it)
            }
            title?.text = child.title
            timeCountdown?.text = child.countDownTime
        }
    }

    inner class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView? = itemView.title
        private val message: TextView? = itemView.message
        private val dateTime: TextView? = itemView.date_time
        private val willCome: TextView? = itemView.will_come
        private val poster: ImageView? = itemView.poster

        fun bind(child: GeneralEvents) {
            itemView.setOnClickListener {
                onClick.invoke(child)
            }

            poster?.visibility = View.VISIBLE
            poster?.let { Picasso.get().load(child.poster_url).fit().centerCrop().into(it) }

            title?.text = child.title
            dateTime?.text = getNeededDateTime(child.date)
            willCome?.text = child.subscribers.toString()
            message?.text = child.message
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getNeededDateTime(source: String?): String? {
        val originalFormat: DateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val targetFormat: DateFormat = SimpleDateFormat("dd MMM HH:mm")
        var date: Date?

        source?.let {
            date = originalFormat.parse(source)
            return targetFormat.format(date!!)
        }
        return ""
    }
}
