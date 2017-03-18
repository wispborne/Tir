package com.thunderclouddev.tirforgoodreads.ui

import android.databinding.BindingAdapter
import android.text.format.DateUtils
import android.widget.TextView
import org.threeten.bp.Instant

/**
 * Created by David Whitman on 18 Mar, 2017.
 */
@BindingAdapter("binding:timeStamp")
fun setText(textView: TextView, millis: Long) {
    textView.text = DateUtils.getRelativeTimeSpanString(millis, Instant.now().toEpochMilli(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL)
}