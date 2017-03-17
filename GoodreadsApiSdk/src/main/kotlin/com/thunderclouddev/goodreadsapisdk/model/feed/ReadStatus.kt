package com.thunderclouddev.goodreadsapisdk.model.feed

import com.thunderclouddev.goodreadsapisdk.model.Review
import org.threeten.bp.Instant

/**
 * An update on an user's reading status
 * @author David Whitman on 17 Mar, 2017.
 */
data class ReadStatus(
        val reviewId: Int,
        val userId: Int,
        val oldStatus: String,
        val status: String,
        val updatedAt: Instant,
        val review: Review
) : FeedItem.FeedObject