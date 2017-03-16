package com.thunderclouddev.tirforgoodreads.api.model

import org.simpleframework.xml.Element

/**
 * @author David Whitman on 16 Mar, 2017.
 */
@Element(name = "object")
data class UpdateObject(
        @field:Element(name = "read_status", required = false) var readStatus: ReadStatus = ReadStatus(),
        @field:Element(name = "book", required = false) var book: Book = Book()
)

@Element(name = "read_status")
data class ReadStatus(
        @field:Element(name = "id") var id: Int = 0,
        @field:Element(name = "review_id", required = false) var reviewId: Int = 0,
        @field:Element(name = "user_id", required = false) var userId: Int = 0,
        @field:Element(name = "old_status", required = false) var oldStatus: String = "",
        @field:Element(name = "status", required = false) var status: String = "",
        @field:Element(name = "updated_at", required = false) var updatedAt: String = "",
        @field:Element(name = "review", required = false) var review: Review = Review()
)