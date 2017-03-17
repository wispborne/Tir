package com.thunderclouddev.goodreadsapisdk.model

/**
 * @author David Whitman on 17 Mar, 2017.
 */
data class Review(
        val id: Int,
        val userId: Int,
        val bookId: Int,
        val rating: Int,
        val readStatus: String,
        val sellFlag: String,
        val review: String,
        val recommendation: String,
        val readAt: String,
        val updatedAt: String,
        val createdAt: String,
        val commentsCount: Int,
        val weight: Int,
        val ratingsSum: Int,
        val ratingsCount: Int,
        val notes: String,
        val spoilerFlag: String,
        val recommenderUserId1: Int,
        val recommenderUserName1: Int,
        val workId: Int,
        val readCount: String,
        val lastCommentAt: String,
        val startedAt: String,
        val hiddenFlag: String,
        val languageCode: Int,
        val lastRevisionAt: String,
        val nonFriendsRatingCount: Int,
        val book: Book
)