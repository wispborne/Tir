package com.thunderclouddev.tirforgoodreads.database.model

import io.requery.*
import io.requery.query.MutableResult

/**
 * Created by David Whitman on 12 Mar, 2017.
 */
@Entity
interface Book {
    @get:Key val id: String
    @get:Index val isbn: String
    @get:Index val isbn13: String
    @get:Index val title: String
    val reviewsCount: Long
    val titleWithoutSeries: String
    val imageUrl: String
    val smallImageUrl: String
    val largeImageUrl: String
    val link: String
    val numPages: Int
    val format: String
    val editionInformation: String
    val publisher: String
    val publicationDay: Int
    val publicationYear: Int
    val publicationMonth: Int
    val averageRating: Double
    val ratingsCount: Long
    val description: String
    val published: String

    @get:ManyToMany()
    @get:JunctionTable()
    val authors: MutableResult<Author>

    // iso 8601 for human readability
    val dateTimeCached: String
}