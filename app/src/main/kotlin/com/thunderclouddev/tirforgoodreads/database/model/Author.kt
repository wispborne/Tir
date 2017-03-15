package com.thunderclouddev.tirforgoodreads.database.model

import io.requery.Entity
import io.requery.Index
import io.requery.Key
import io.requery.ManyToMany
import io.requery.query.MutableResult

/**
 * Created by David Whitman on 12 Mar, 2017.
 */
@Entity
interface Author {
    @get:Key val id: String
    @get:Index val name: String
    val link: String
    val imageUrl: String
    val role: String
    val smallImageUrl: String
    val averageRating: Double
    val ratingsCount: Long
    val textReviewsCount: Long
    val dateTimeCached: String

    @get:ManyToMany val books: MutableResult<Book>
}