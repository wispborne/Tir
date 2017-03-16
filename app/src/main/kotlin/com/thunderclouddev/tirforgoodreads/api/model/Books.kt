package com.thunderclouddev.tirforgoodreads.api.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList

/**
 * Created by David Whitman on 11 Mar, 2017.
 */
data class Books(
        @field:Element(name = "start", required = false) var start: Int = 0,
        @field:Element(name = "end", required = false) var end: Int = 0,
        @field:Element(name = "total", required = false) var total: Int = 0,
        @field:ElementList(inline = true) var items: ArrayList<Book> = arrayListOf()
)