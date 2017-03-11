package com.thunderclouddev.tirforgoodreads.api.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList

/**
 * @author David Whitman on 11 Mar, 2017.
 */
data class Book(val id: Int,
    @field:Element(name = "isbn", required = false) var isbn: String,
    @field:Element(name = "isbn13", required = false) var isbn13: String,
    @field:Element(name = "title", required = false) var title: String,
    @field:Element(name = "text_reviews_count", required = false) var reviewsCount: Long,
    @field:Element(name = "title_without_series", required = false) var titleWithoutSeries: String,
    @field:Element(name = "image_url", required = false) var imageUrl: String,
    @field:Element(name = "small_image_url", required = false) var smallImageUrl: String,
    @field:Element(name = "large_image_url", required = false) var largeImageUrl: String,
    @field:Element(name = "link", required = false) var link: String,
    @field:Element(name = "num_pages", required = false) var numPages: Int,
    @field:Element(name = "format", required = false) var format: String, // eg Hardcover
    @field:Element(name = "edition_information", required = false) var editionInformation: String,
    @field:Element(name = "publisher", required = false) var publisher: String,
    @field:Element(name = "publication_day", required = false) var publicationDay: Int,
    @field:Element(name = "publication_year", required = false) var publicationYear: Int,
    @field:Element(name = "publication_month", required = false) var publicationMonth: Int,
    @field:Element(name = "average_rating", required = false) var averageRating: Double,
    @field:Element(name = "ratings_count", required = false) var ratingsCount: Long,
    @field:Element(name = "description", required = false) var description: String,
    @field:Element(name = "published", required = false) var published: String, // Year
    @field:ElementList(name = "authors", required = false,
        inline = true) var authors: ArrayList<Author>
) {
    constructor() : this(0, "", "", "", 0, "", "", "", "", "", 0, "", "", "", 0, 0, 0, 0.00, 0, "",
        "", arrayListOf())
}