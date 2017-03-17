package com.thunderclouddev.goodreadsapisdk.api

import org.simpleframework.xml.Element

/**
 * Created by David Whitman on 16 Mar, 2017.
 */
@Element(name = "actor")
internal data class Actor(
        @field:Element(name = "id") var id: Int = 0,
        @field:Element(name = "name", required = false) var name: String = "",
        @field:Element(name = "image_url", required = false) var image_url: String = "",
        @field:Element(name = "link", required = false) var link: String = ""
)