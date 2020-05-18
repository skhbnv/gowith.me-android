package com.iitu.gowithme.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iitu.gowithme.R
import com.iitu.gowithme.data.network.event_list.EventListType
import com.iitu.gowithme.ui.home.model.NamedEventList
import com.iitu.gowithme.util.inflate
import kotlinx.android.synthetic.main.item_home_main.view.*

class HomeMainRecyclerAdapter: RecyclerView.Adapter<HomeMainRecyclerAdapter.HomeRecyclerViewHolder>() {

    private val viewPool by lazy { RecyclerView.RecycledViewPool() }
    private val eventLists = ArrayList<NamedEventList>()
    private var onEventClickedListener: ((Int) -> Unit)? = null
    var onAllButtonClicked: ((EventListType) -> Unit)? = null

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
        holder.bindView(eventLists[position])
    }

    inner class HomeRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(namedEventList: NamedEventList) {
            with(itemView) {
                title.text = when(namedEventList.type) {
                    EventListType.SPECIAL -> context.getString(R.string.title_special_list)
                    EventListType.MOST_VIEWED -> context.getString(R.string.title_most_viewed_list)
                    EventListType.UPCOMING -> context.getString(R.string.title_upcoming_list)
                    EventListType.NEW -> context.getString(R.string.title_new_list)
                    else -> context.getString(R.string.title_event_list)
                }
                innerRecycler.setRecycledViewPool(viewPool)
                innerRecycler.adapter = HorizontalEventRecyclerAdapter(namedEventList.events).apply {
                    setOnEventClickedListener { onEventClickedListener?.invoke(it) }
                }
                allButton.setOnClickListener { onAllButtonClicked?.invoke(namedEventList.type) }
            }
        }
    }
}