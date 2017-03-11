package com.thunderclouddev.tirforgoodreads.api

import com.thunderclouddev.tirforgoodreads.api.model.BooksByAuthorResponse
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by David Whitman on 11 Mar, 2017.
 */
interface GoodreadsApi {
    @GET("/author/list/18541?format=xml&key=KUtQGqdhmKy1nUyQnFZRzA")
    fun getAuthorBooks(): Observable<BooksByAuthorResponse>
}