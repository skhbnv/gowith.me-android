package com.example.gowithme.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.R
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.util.inflate
import kotlinx.android.synthetic.main.item_home_main.view.*

class HomeMainRecyclerAdapter: RecyclerView.Adapter<HomeMainRecyclerAdapter.HomeRecyclerViewHolder>() {

    private val viewPool by lazy { RecyclerView.RecycledViewPool() }
    private val eventsListNames = ArrayList<String>()
    private val eventsLists = ArrayList<List<EventResponse>>()

    fun addEventList(eventsName: String, events: List<EventResponse>) {
        eventsListNames.add(eventsName)
        eventsLists.add(events)
        notifyItemInserted(eventsListNames.lastIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder =
        HomeRecyclerViewHolder(parent.inflate(R.layout.item_home_main))

    override fun getItemCount(): Int = eventsLists.size

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        holder.bindView(eventsListNames[position], eventsLists[position])
    }

    inner class HomeRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(eventsName: String, events: List<EventResponse>) {
            with(itemView) {
                title.text = eventsName
                innerRecycler.setRecycledViewPool(viewPool)
                innerRecycler.adapter = HorizontalEventRecyclerAdapter(events)
            }
        }
    }
}