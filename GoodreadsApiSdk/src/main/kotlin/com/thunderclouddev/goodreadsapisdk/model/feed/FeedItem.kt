package com.thunderclouddev.goodreadsapisdk.model.feed

import com.thunderclouddev.goodreadsapisdk.model.Actor

/**
 * An update on the user's news feed.
 * Created by David Whitman on 17 Mar, 2017.
 */
interface FeedItem {
    val actionText: String
    val link: String
    val imageUrl: String
    val actor: Actor
    val updatedAt: org.threeten.bp.Instant
    val obj: FeedObject

    interface FeedObject

    enum class Type {
        ReadStatus,
        Review,
        Book,
        Unknown
    }
}