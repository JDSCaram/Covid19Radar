package br.com.jdscaram.covid19radar.util

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("present")
fun View.setPresent(value: Boolean?) {
    value?.let {
        visibility = if (it) View.VISIBLE else View.GONE
    }
}