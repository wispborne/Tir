package com.thunderclouddev.tirforgoodreads.model

/**
 * Created by David Whitman on 11 Mar, 2017.
 */
data class Author(
        val id: Int,
        val name: String,
        val link: String,
        val imageUrl: String,
        val role: String,
        val smallImageUrl: String,
        val averageRating: Double,
        val ratingsCount: Long,
        val textReviewsCount: Long
//    val books: List<Book>
)