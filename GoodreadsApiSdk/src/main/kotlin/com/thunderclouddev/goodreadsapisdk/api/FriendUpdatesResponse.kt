package com.thunderclouddev.goodreadsapisdk.api

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

/**
 * @author David Whitman on 16 Mar, 2017.
 */
@Root(name = "GoodreadsResponse")
internal data class FriendUpdatesResponse(
        @field:Element(name = "Request") var request: Request = Request(),
        @field:Element(name = "updates") var updates: Updates = Updates()
)