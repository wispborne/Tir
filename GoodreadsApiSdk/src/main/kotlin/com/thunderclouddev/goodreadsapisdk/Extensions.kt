package com.thunderclouddev.goodreadsapisdk

import org.threeten.bp.Instant
import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by David Whitman on 17 Mar, 2017.
 */
fun CharSequence.toInstant(defaultValue: Instant = Instant.MIN.plusMillis(1), formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME): Instant {
    try {
        return Instant.from(formatter.parse(this))
    } catch (exception: Exception) {
        return if (defaultValue == Instant.MIN.plusMillis(1)) {
            throw exception
        } else
            defaultValue
    }
}