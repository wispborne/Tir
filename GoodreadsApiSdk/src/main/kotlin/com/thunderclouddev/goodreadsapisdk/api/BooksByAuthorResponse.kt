package com.thunderclouddev.goodreadsapisdk.api

import com.thunderclouddev.goodreadsapisdk.api.Author
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

/**
 * Created by David Whitman on 11 Mar, 2017.
 */
@Root(name = "GoodreadsResponse")
internal data class BooksByAuthorResponse(
        @field:Element(name = "Request") var request: Request,
        @field:Element(name = "author") var author: Author
) {
    constructor() : this(Request(), Author())
}