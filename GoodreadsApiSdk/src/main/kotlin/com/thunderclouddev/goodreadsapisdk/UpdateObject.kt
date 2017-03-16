package com.thunderclouddev.goodreadsapisdk

import org.simpleframework.xml.Element

/**
 * @author David Whitman on 16 Mar, 2017.
 */
@Element(name = "object")
data class UpdateObject(
        @field:Element(name = "read_status", required = false) var readStatus: ReadStatus = ReadStatus(),
        @field:Element(name = "book", required = false) var book: Book = Book()
)