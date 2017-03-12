package com.thunderclouddev.tirforgoodreads

import com.thunderclouddev.tirforgoodreads.api.model.Author
import com.thunderclouddev.tirforgoodreads.api.model.Book

/**
 * @author David Whitman on 11 Mar, 2017.
 */
fun Book.mapToViewModel() = com.thunderclouddev.tirforgoodreads.model.Book(
        this.id,
        this.isbn,
        this.isbn13,
        this.title,
        this.reviewsCount,
        this.titleWithoutSeries,
        this.imageUrl,
        this.smallImageUrl,
        this.largeImageUrl,
        this.link,
        this.numPages,
        this.format,
        this.editionInformation,
        this.publisher,
        this.publicationDay,
        this.publicationYear,
        this.publicationMonth,
        this.averageRating,
        this.ratingsCount,
        this.description,
        this.published,
        this.authors.items.map(Author::mapToViewModel)
)

fun Author.mapToViewModel() = com.thunderclouddev.tirforgoodreads.model.Author(
        this.id,
        this.name,
        this.link,
        this.imageUrl,
        this.role,
        this.smallImageUrl,
        this.averageRating,
        this.ratingsCount,
        this.textReviewsCount
//        this.books.items.
)