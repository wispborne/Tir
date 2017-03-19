package com.thunderclouddev.goodreadsapisdk.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Created by David Whitman on 11 Mar, 2017.
 *
 * Thank you to https://github.com/fabiomsr/kotlin-xml-examples
 */
internal interface GoodreadsApiDefinition {
    @GET("/author/list/{authorId}?format=xml&key=KUtQGqdhmKy1nUyQnFZRzA")
    fun getAuthorBooks(@Path("authorId") authorId: String): Single<BooksByAuthorResponse>

    @GET("/updates/friends.xml")
    fun getFriendUpdates(@QueryMap options: Map<String, String> = emptyMap()): Single<FriendUpdatesResponse>

    @GET("/api/v3/updates/newsfeed.xm")
    fun getFriendUpdatesV3(@QueryMap options: Map<String, String> = emptyMap()): Single<FriendUpdatesResponse>
}