package com.thunderclouddev.goodreadsapisdk.model

/**
 * An entity that did something. Could be a friend, an author, something like that.
 * @author David Whitman on 17 Mar, 2017.
 */
class Actor(
        val id: Int,
        val name: String,
        val imageUrl: String,
        val link: String
)