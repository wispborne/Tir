package com.thunderclouddev.goodreadsapisdk.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by David Whitman on 11 Mar, 2017.
 *
 * Thank you to https://github.com/fabiomsr/kotlin-xml-examples
 */
internal interface GoodreadsApiDefinition {
    @GET("/author/list/{authorId}?format=xml&key=KUtQGqdhmKy1nUyQnFZRzA")
    fun getAuthorBooks(@Path("authorId") authorId: String): Single<BooksByAuthorResponse>

    @GET("/updates/friends.xml")
    fun getFriendUpdates(): Single<FriendUpdatesResponse>
}