package com.thunderclouddev.goodreadsapisdk.api

import com.thunderclouddev.goodreadsapisdk.api.Author
import com.thunderclouddev.goodreadsapisdk.api.Authors
import org.simpleframework.xml.Element

/**
 * @author David Whitman on 11 Mar, 2017.
 */
internal data class Book(@field:Element(name = "id") var id: String = "",
                @field:Element(name = "isbn", required = false) var isbn: String = "",
                @field:Element(name = "isbn13", required = false) var isbn13: String = "",
                @field:Element(name = "title", required = false) var title: String = "",
                @field:Element(name = "text_reviews_count", required = false) var reviewsCount: Long = 0,
                @field:Element(name = "title_without_series", required = false) var titleWithoutSeries: String = "",
                @field:Element(name = "image_url", required = false) var imageUrl: String = "",
                @field:Element(name = "small_image_url", required = false) var smallImageUrl: String = "",
                @field:Element(name = "large_image_url", required = false) var largeImageUrl: String = "",
                @field:Element(name = "link", required = false) var link: String = "",
                @field:Element(name = "num_pages", required = false) var numPages: Int = 0,
                @field:Element(name = "format", required = false) var format: String = "", // eg Hardcover
                @field:Element(name = "edition_information", required = false) var editionInformation: String = "",
                @field:Element(name = "publisher", required = false) var publisher: String = "",
                @field:Element(name = "publication_day", required = false) var publicationDay: Int = 0,
                @field:Element(name = "publication_year", required = false) var publicationYear: Int = 0,
                @field:Element(name = "publication_month", required = false) var publicationMonth: Int = 0,
                @field:Element(name = "average_rating", required = false) var averageRating: Double = 0.00,
                @field:Element(name = "ratings_count", required = false) var ratingsCount: Long = 0,
                @field:Element(name = "description", required = false) var description: String = "",
                @field:Element(name = "published", required = false) var published: String = "", // Year
                @field:Element(name = "author", required = false) var author: Author = Author(),
                @field:Element(name = "authors", required = false) var authors: Authors = Authors()
)