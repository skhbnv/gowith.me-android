package com.example.gowithme.ui.event_page

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.responses.User
import com.example.gowithme.ui.adapters.EventsAdapter
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object PageDataBinding {
    @JvmStatic
    @SuppressLint("SimpleDateFormat")
    @BindingAdapter("android:date")
    fun getNeededDateTime(view: TextView, source: String?) {
//            2020-01-15T18:00:00Z
        val originalFormat: DateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val targetFormat: DateFormat = SimpleDateFormat("dd MMM HH:mm")
        var date: Date?

        source?.let {
            date = originalFormat.parse(source)
            view.text = targetFormat.format(date!!)
        }
    }

    @JvmStatic
    @BindingAdapter("android:adapter")
    fun rvAdapter(rv: RecyclerView, adapter: RecyclerView.Adapter<EventsAdapter.EventsViewHolder>?) {
        rv.adapter = adapter
    }
    @JvmStatic
    @BindingAdapter("android:image_url")
    fun setImage(img: ImageView, src: String?){
        val pic = Picasso.get()
        pic.isLoggingEnabled = true
        pic.load(src).fit().centerCrop().into(img)
    }
    @JvmStatic
    @BindingAdapter("android:name_concat")
    fun setName(view: TextView, user: User?){
        val format = "${user?.first_name} ${user?.last_name}"
        view.text = format
    }

}