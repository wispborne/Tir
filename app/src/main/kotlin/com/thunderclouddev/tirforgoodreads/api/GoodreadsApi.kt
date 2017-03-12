package com.thunderclouddev.tirforgoodreads.api

import com.thunderclouddev.tirforgoodreads.api.model.BooksByAuthorResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by David Whitman on 11 Mar, 2017.
 *
 * Thank you to https://github.com/fabiomsr/kotlin-xml-examples
 */
interface GoodreadsApi {
    @GET("/author/list/{authorId}?format=xml&key=KUtQGqdhmKy1nUyQnFZRzA")
    fun getAuthorBooks(@Path("authorId") authorId: String): Single<BooksByAuthorResponse>
}