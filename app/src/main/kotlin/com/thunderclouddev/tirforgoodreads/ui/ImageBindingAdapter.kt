package com.thunderclouddev.tirforgoodreads.ui

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.thunderclouddev.tirforgoodreads.R
import com.thunderclouddev.tirforgoodreads.getOrNullIfBlank
import com.thunderclouddev.tirforgoodreads.uri.Uri

/**
 * Created by David Whitman on 18 Mar, 2017.
 */
@BindingAdapter("binding:imageUri")
fun setImage(imageView: ImageView, uri: Uri) {
    Picasso.with(imageView.context)
            .load(uri.toString().getOrNullIfBlank())
            .placeholder(R.drawable.book)
            .into(imageView)
}