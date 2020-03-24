package com.example.gowithme.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.R
import com.example.gowithme.responses.GeneralEvents
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_horizontal_item.view.*

class ChildAdapter(private val children : List<GeneralEvents>)
    : RecyclerView.Adapter<ChildAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val v =  LayoutInflater.from(parent.context)
            .inflate(R.layout.card_horizontal_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val child = children[position]
        Picasso.get().load(child.poster_url).fit().centerCrop().into(holder.imageView)
        holder.textView.text = child.title
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textView : TextView = itemView.child_textView
        val imageView: ImageView = itemView.child_imageView
    }
}