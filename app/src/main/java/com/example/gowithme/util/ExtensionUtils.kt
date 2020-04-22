package com.example.gowithme.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.koin.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun ViewGroup.inflate(layout: Int, attachToRoot: Boolean = false): View = LayoutInflater.from(this.context).inflate(layout, this, attachToRoot)

fun <T : ViewDataBinding?> ViewGroup.inflateBinding(layout: Int): T = DataBindingUtil.inflate<T>(LayoutInflater.from(this.context), layout, this, false)

fun <T : ViewDataBinding?> LayoutInflater.inflateBinding(viewGroup: ViewGroup?, layout: Int): T = DataBindingUtil.inflate<T>(this, layout, viewGroup, false)

fun String.showToast(context: Context?) {
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}

inline fun <reified VM : ViewModel> Fragment.sharedGraphViewModel(
    @IdRes navGraphId: Int,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) = lazy {
    val owner = findNavController().getViewModelStoreOwner(navGraphId)

    getKoin().getViewModel(owner, VM::class, qualifier, parameters)
}

fun Date.toIsoFormat() =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault()).format(this)

fun Date.format(pattern: String) =  SimpleDateFormat(pattern, Locale.getDefault()).format(this)

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