package com.thunderclouddev.goodreadsapisdk.model.feed

import com.thunderclouddev.goodreadsapisdk.model.Actor
import com.thunderclouddev.goodreadsapisdk.model.Book
import org.threeten.bp.Instant

/**
 * @author David Whitman on 17 Mar, 2017.
 */
data class ReviewFeedItem(override val actionText: String,
                     override val link: String,
                     override val imageUrl: String,
                     override val actor: Actor,
                     override val updatedAt: Instant,
                     override val obj: Book)
    : FeedItem