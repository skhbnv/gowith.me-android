package com.iitu.gowithme.ui.event_page.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iitu.gowithme.R
import com.iitu.gowithme.data.models.response.CommentResponse
import com.iitu.gowithme.databinding.ItemCommentBinding
import com.iitu.gowithme.util.inflateBinding

class CommentRecyclerAdapter() : RecyclerView.Adapter<CommentRecyclerAdapter.CommentViewHolder>() {

    private val comments = ArrayList<CommentResponse>()
    val commentsAmount get() = comments.size

    fun setComment(data: List<CommentResponse>) {
        comments.clear()
        comments.addAll(data)
        notifyDataSetChanged()
    }

    fun addComment(data: CommentResponse) {
        comments.add(data)
        notifyItemInserted(comments.lastIndex)
    }


    inner class CommentViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(comment: CommentResponse) {
            with(binding) {
                author.text = "${comment.author.lastName} ${comment.author.firstName}"
                content.text = comment.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder =
        CommentViewHolder(parent.inflateBinding<ItemCommentBinding>(R.layout.item_comment))

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }
}