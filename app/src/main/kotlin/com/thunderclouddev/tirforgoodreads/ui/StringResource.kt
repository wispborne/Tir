package com.thunderclouddev.tirforgoodreads.ui

import android.content.res.Resources
import android.support.annotation.StringRes
import com.thunderclouddev.tirforgoodreads.isNotNullOrBlank

/**
 * @author David Whitman on 18 Mar, 2017.
 */
class StringResource(private val resources: Resources) {
    fun getString(@StringRes stringId: Int, vararg args: String): String = resources.getString(stringId, *args.filter(String::isNotNullOrBlank).toTypedArray())
}