package com.example.gowithme.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.R
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.ui.home.model.NamedEventList
import com.example.gowithme.util.inflate
import kotlinx.android.synthetic.main.item_home_main.view.*

class HomeMainRecyclerAdapter: RecyclerView.Adapter<HomeMainRecyclerAdapter.HomeRecyclerViewHolder>() {

    private val viewPool by lazy { RecyclerView.RecycledViewPool() }
    private val eventLists = ArrayList<NamedEventList>()
    private var onEventClickedListener: ((Int) -> Unit)? = null

    fun setOnEventClickedListener(listener: (Int) -> Unit) {
        onEventClickedListener = listener
    }

    fun setData(data : List<NamedEventList>) {
        eventLists.clear()
        eventLists.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder =
        HomeRecyclerViewHolder(parent.inflate(R.layout.item_home_main))

    override fun getItemCount(): Int = eventLists.size

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        holder.bindView(eventLists[position].name, eventLists[position].events)
    }

    inner class HomeRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(eventsName: String, events: List<EventResponse>) {
            with(itemView) {
                title.text = eventsName
                innerRecycler.setRecycledViewPool(viewPool)
                innerRecycler.adapter = HorizontalEventRecyclerAdapter(events).apply {
                    setOnEventClickedListener { onEventClickedListener?.invoke(it) }
                }
            }
        }
    }
}