package com.example.gowithme.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

fun ViewGroup.inflate(layout: Int, attachToRoot: Boolean = false): View = LayoutInflater.from(this.context).inflate(layout, this, attachToRoot)

fun String.showToast(context: Context?) {
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}

fun Calendar.showDateTimePicker(context: Context, callback: (calendar: Calendar) -> Unit) {
    val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        this.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        val timeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            this.apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }

            callback.invoke(this)
        }
        TimePickerDialog(
            context,
            timeListener,
            this.get(Calendar.HOUR_OF_DAY),
            this.get(Calendar.MINUTE),
            true
        ).show()
    }

    DatePickerDialog(
        context,
        dateListener,
        this.get(Calendar.YEAR),
        this.get(Calendar.MONTH),
        this.get(Calendar.DAY_OF_MONTH)
    ).apply {
        datePicker.minDate = Date().time
    }.show()
}