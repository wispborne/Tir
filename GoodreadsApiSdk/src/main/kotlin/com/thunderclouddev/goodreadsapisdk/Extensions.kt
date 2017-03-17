package com.thunderclouddev.goodreadsapisdk

import org.threeten.bp.Instant

/**
 * Created by David Whitman on 17 Mar, 2017.
 */
fun CharSequence.toInstant(defaultValue: Instant): Instant {
    try {
        return Instant.parse(this)
    } catch (exception: Exception) {
        return defaultValue
    }
}