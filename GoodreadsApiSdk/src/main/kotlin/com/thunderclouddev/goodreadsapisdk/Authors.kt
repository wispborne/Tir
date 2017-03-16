package com.thunderclouddev.goodreadsapisdk

import org.simpleframework.xml.ElementList

/**
 * Created by David Whitman on 16 Mar, 2017.
 */
data class Authors(
        @field:ElementList(inline = true, entry = "author") var items: ArrayList<Author> = arrayListOf()
)