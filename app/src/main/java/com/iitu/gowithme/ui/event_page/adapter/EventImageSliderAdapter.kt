package com.iitu.gowithme.ui.event_page.adapter

import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.iitu.gowithme.BuildConfig
import com.iitu.gowithme.R
import com.iitu.gowithme.data.models.response.ImageResponse
import com.iitu.gowithme.util.inflate
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.slider_item_event_image.view.*

class EventImageSliderAdapter : SliderViewAdapter<EventImageSliderAdapter.EventImageViewHolder>() {

    private val images = ArrayList<ImageResponse>()
    var onItemClicked: ((uri: String) -> Unit)? = null

    fun setImages(data: List<ImageResponse>) {
        images.clear()
        images.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): EventImageViewHolder =
        EventImageViewHolder(parent.inflate(R.layout.slider_item_event_image))

    override fun onBindViewHolder(viewHolder: EventImageViewHolder?, position: Int) {
        viewHolder?.bind(images[position])
    }

    override fun getCount(): Int = images.size


    inner class EventImageViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        fun bind(imageResponse: ImageResponse) {
            val uri = BuildConfig.BASE_URL + imageResponse.image.substring(1)
            Glide.with(itemView.context)
                .load(uri)
                .into(itemView.imageView)
            itemView.setOnClickListener {
                onItemClicked?.invoke(uri)
            }
        }
    }
}