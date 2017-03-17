package com.thunderclouddev.goodreadsapisdk.model.feed

import com.thunderclouddev.goodreadsapisdk.model.Actor
import org.threeten.bp.Instant

/**
 * Created by David Whitman on 17 Mar, 2017.
 */
data class ReadStatusFeedItem(override val actionText: String,
                              override val link: String,
                              override val imageUrl: String,
                              override val actor: Actor,
                              override val updatedAt: Instant,
                              override val obj: ReadStatus
) : FeedItem