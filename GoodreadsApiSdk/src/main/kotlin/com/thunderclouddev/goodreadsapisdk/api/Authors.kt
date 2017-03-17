package com.thunderclouddev.goodreadsapisdk.api

import org.simpleframework.xml.ElementList

/**
 * Created by David Whitman on 16 Mar, 2017.
 */
internal data class Authors(
        @field:ElementList(inline = true, entry = "author") var items: ArrayList<Author> = arrayListOf()
)