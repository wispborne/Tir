package com.thunderclouddev.goodreadsapisdk.model

import com.thunderclouddev.goodreadsapisdk.model.Author
import com.thunderclouddev.goodreadsapisdk.model.feed.FeedItem

/**
 * Created by David Whitman on 11 Mar, 2017.
 */
data class Book(
        var id: String,
        var isbn: String,
        var isbn13: String,
        var title: String,
        var reviewsCount: Long,
        var titleWithoutSeries: String,
        var imageUrl: String,
        var smallImageUrl: String,
        var largeImageUrl: String,
        var link: String,
        var numPages: Int,
        var format: String, // eg Hardcover
        var editionInformation: String,
        var publisher: String,
        var publicationDay: Int,
        var publicationYear: Int,
        var publicationMonth: Int,
        var averageRating: Double,
        var ratingsCount: Long,
        var description: String,
        var published: String, // Year
        var authors: List<Author>
) : FeedItem.FeedObject