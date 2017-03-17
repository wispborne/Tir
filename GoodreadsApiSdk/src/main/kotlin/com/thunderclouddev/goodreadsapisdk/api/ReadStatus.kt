package com.thunderclouddev.goodreadsapisdk.api

import org.simpleframework.xml.Element

/**
 * Created by David Whitman on 16 Mar, 2017.
 */
@Element(name = "read_status")
internal data class ReadStatus(
        @field:Element(name = "id") var id: Int = 0,
        @field:Element(name = "review_id", required = false) var reviewId: Int = 0,
        @field:Element(name = "user_id", required = false) var userId: Int = 0,
        @field:Element(name = "old_status", required = false) var oldStatus: String = "",
        @field:Element(name = "status", required = false) var status: String = "",
        @field:Element(name = "updated_at", required = false) var updatedAt: String = "",
        @field:Element(name = "review", required = false) var review: Review = Review()
)