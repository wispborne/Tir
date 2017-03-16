package com.thunderclouddev.goodreadsapisdk

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList

/**
 * Created by David Whitman on 11 Mar, 2017.
 */
data class Author(
        @field:Element(name = "id") var id: String = "",
        @field:Element(name = "name") var name: String = "",
        @field:Element(name = "link", required = false) var link: String = "",
        @field:Element(name = "image_url", required = false) var imageUrl: String = "",
        @field:Element(name = "role", required = false) var role: String = "",
        @field:Element(name = "small_image_url", required = false) var smallImageUrl: String = "",
        @field:Element(name = "average_rating", required = false) var averageRating: Double = 0.00,
        @field:Element(name = "ratings_count", required = false) var ratingsCount: Long = 0,
        @field:Element(name = "text_reviews_count", required = false) var textReviewsCount: Long = 0,
        @field:Element(name = "books", required = false) var books: Books = Books()
)