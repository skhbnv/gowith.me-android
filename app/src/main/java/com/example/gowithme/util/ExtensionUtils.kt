package com.example.gowithme.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

fun ViewGroup.inflate(layout: Int): View = LayoutInflater.from(this.context).inflate(layout, this, false)

fun String.showToast(context: Context?) {
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}