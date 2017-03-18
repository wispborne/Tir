package com.thunderclouddev.tirforgoodreads.ui

import android.databinding.BindingAdapter
import android.text.Html
import android.text.Spannable
import android.widget.TextView
import android.text.Spanned



/**
 * Created by David Whitman on 18 Mar, 2017.
 */
@BindingAdapter("binding:htmlText")
fun setText(textView: TextView, string: String) {
    textView.text = fromHtml(string)
}

private fun fromHtml(html: String): Spanned {
    val result: Spanned
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        result = Html.fromHtml(html)
    }
    return result
}