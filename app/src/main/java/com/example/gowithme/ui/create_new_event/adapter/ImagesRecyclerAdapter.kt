package com.example.gowithme.ui.create_new_event.adapter

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gowithme.R
import com.example.gowithme.util.inflate
import kotlinx.android.synthetic.main.item_event_image.view.*

class ImagesRecyclerAdapter(
    val addImageButtonClickListener: () -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val imagesUris = ArrayList<Uri>()

    fun addImageUri(uri: Uri) {
        imagesUris.add(uri)
        notifyItemInserted(1)
    }

    override fun getItemViewType(position: Int): Int =
        when(position) {
            0 -> ADD_IMAGE_BUTTON_VIEW_TYPE
            else -> IMAGE_VIEW_TYPE
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ADD_IMAGE_BUTTON_VIEW_TYPE -> AddImageViewHolder(parent.inflate(R.layout.item_add_event_image_button))
            IMAGE_VIEW_TYPE -> ImageViewHolder(parent.inflate(R.layout.item_event_image))
            else -> throw IllegalArgumentException("viewType not implemented")
        }

    override fun getItemCount(): Int = imagesUris.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(position) {
            0 -> (holder as AddImageViewHolder).bind()
            else -> (holder as ImageViewHolder).bind(imagesUris[imagesUris.size - position])
        }
    }


    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val glide = Glide.with(itemView.context)
        fun bind(imageUri: Uri) {
            with(itemView) {
                glide.load(imageUri).into(imageView)
            }
        }
    }

    inner class AddImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind() {
            itemView.setOnClickListener {
                addImageButtonClickListener()
            }
        }
    }

    companion object {
        const val ADD_IMAGE_BUTTON_VIEW_TYPE = 0
        const val IMAGE_VIEW_TYPE = 1
    }

}