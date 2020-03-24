package com.example.gowithme.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.R
import com.example.gowithme.models.ParentModel
import kotlinx.android.synthetic.main.card_horizontal.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ParentAdapter(private val parents : List<ParentModel>) :
    RecyclerView.Adapter<ParentAdapter.ViewHolder>(){
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_horizontal,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return parents.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val parent = parents[position]
        holder.textView?.text = parent.title
        val childLayoutManager = LinearLayoutManager(
            holder.recyclerView?.context,
            RecyclerView.HORIZONTAL,
            false)
        childLayoutManager.initialPrefetchItemCount = 4
        holder.recyclerView?.apply {
            layoutManager = childLayoutManager
            adapter = ChildAdapter(parent.children)
            setRecycledViewPool(viewPool)
        }
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val recyclerView : RecyclerView? = itemView.rv_child
        val textView: TextView? = itemView.horizontal_title

        fun bind(item: ParentModel){

        }
    }
}