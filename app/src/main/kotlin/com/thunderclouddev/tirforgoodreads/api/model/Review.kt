package com.thunderclouddev.tirforgoodreads.api.model

import org.simpleframework.xml.Element

/**
 * @author David Whitman on 16 Mar, 2017.
 */
@Element(name = "Review")
data class Review(
        @field:Element(name = "id") var id: Int = 0,
        @field:Element(name = "user_id", required = false) var userId: Int = 0,
        @field:Element(name = "book_id", required = false) var bookId: Int = 0,
        @field:Element(name = "rating", required = false) var rating: Int = 0,
        @field:Element(name = "read_status", required = false) var readStatus: String = "",
        @field:Element(name = "sell_flag", required = false) var sellFlag: String = "",
        @field:Element(name = "review", required = false) var review: String = "",
        @field:Element(name = "recommendation", required = false) var recommendation: String = "",
        @field:Element(name = "read_at", required = false) var readAt: String = "",
        @field:Element(name = "updated_at", required = false) var updatedAt: String = "",
        @field:Element(name = "created_at", required = false) var createdAt: String = "",
        @field:Element(name = "comments_count", required = false) var commentsCount: Int = 0,
        @field:Element(name = "weight", required = false) var weight: Int = 0,
        @field:Element(name = "ratings_sum", required = false) var ratingsSum: Int = 0,
        @field:Element(name = "ratings_count", required = false) var ratingsCount: Int = 0,
        @field:Element(name = "notes", required = false) var notes: String = "",
        @field:Element(name = "spoiler_flag", required = false) var spoilerFlag: String = "",
        @field:Element(name = "recommender_user_id1", required = false) var recommenderUserId1: Int = 0,
        @field:Element(name = "recommender_user_name1", required = false) var recommenderUserName1: Int = 0,
        @field:Element(name = "work_id", required = false) var workId: Int = 0,
        @field:Element(name = "read_count", required = false) var readCount: String = "",
        @field:Element(name = "last_comment_at", required = false) var lastCommentAt: String = "",
        @field:Element(name = "started_at", required = false) var startedAt: String = "",
        @field:Element(name = "hidden_flag", required = false) var hiddenFlag: String = "",
        @field:Element(name = "language_code", required = false) var languageCode: Int = 0,
        @field:Element(name = "last_revision_at", required = false) var lastRevisionAt: String = "",
        @field:Element(name = "non_friends_rating_count", required = false) var nonFriendsRatingCount: Int = 0,
        @field:Element(name = "book", required = false) var book: Book = Book()
)