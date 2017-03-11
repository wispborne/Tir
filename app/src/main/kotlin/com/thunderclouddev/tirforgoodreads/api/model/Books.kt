package com.thunderclouddev.tirforgoodreads.api.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList

/**
 * Created by David Whitman on 11 Mar, 2017.
 */
data class Books(
    @field:Element(name = "start", required = false) var start: Int,
    @field:Element(name = "end", required = false) var end: Int,
    @field:Element(name = "total", required = false) var total: Int,
    @field:ElementList(inline = true) var items: ArrayList<Book>
) {
    constructor() : this(0, 0, 0, arrayListOf())
}