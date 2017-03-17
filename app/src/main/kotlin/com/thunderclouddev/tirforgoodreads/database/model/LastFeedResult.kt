package com.thunderclouddev.tirforgoodreads.database.model

import io.requery.Entity
import io.requery.Key

/**
 * @author David Whitman on 17 Mar, 2017.
 */
@Entity
interface LastFeedResult {
    @get:Key val timestamp: String
    val feedJson: String
}