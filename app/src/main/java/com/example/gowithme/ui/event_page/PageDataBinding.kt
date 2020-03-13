package com.example.gowithme.ui.event_page

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
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

}