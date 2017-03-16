package com.thunderclouddev.tirforgoodreads.api.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList

/**
 * @author David Whitman on 16 Mar, 2017.
 */
data class Updates(
        @field:ElementList(inline = true, entry = "update") var updates: ArrayList<Update> = arrayListOf()
)

@Element(name = "update")
data class Update(
        @field:Element(name = "action_text", required = false) var actionText: String = "",
        @field:Element(name = "link", required = false) var link: String = "",
        @field:Element(name = "image_url", required = false) var imageUrl: String = "",
        @field:Element(name = "actor", required = false) var actor: Actor = Actor(),
        @field:Element(name = "updated_at", required = false) var updatedAt: String = "",
        @field:Element(name = "object", required = false) var updateObject: UpdateObject = UpdateObject()
)

@Element(name = "actor")
data class Actor(
        @field:Element(name = "id") var id: Int = 0,
        @field:Element(name = "name", required = false) var name: String = "",
        @field:Element(name = "image_url", required = false) var image_url: String = "",
        @field:Element(name = "link", required = false) var link: String = ""
)