package com.thunderclouddev.goodreadsapisdk

import com.thunderclouddev.goodreadsapisdk.api.GoodreadsApiDefinition
import com.thunderclouddev.goodreadsapisdk.model.Book
import com.thunderclouddev.goodreadsapisdk.model.feed.Feed
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


/**
 * @author David Whitman on 11 Mar, 2017.
 */
class GoodreadsApi(okHttpClient: OkHttpClient) {
    private val goodreadsApiDefinition: GoodreadsApiDefinition

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.goodreads.com")
                .client(okHttpClient)
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                .addCallAdapterFactory(retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.create())
                .build()

        goodreadsApiDefinition = retrofit.create(GoodreadsApiDefinition::class.java)
    }

    fun getAuthorBooks(authorId: String): Single<List<Book>> = goodreadsApiDefinition.getAuthorBooks(authorId)
            .map { it.author.books.items.map { ModelMapper.mapBookApiToView(it) } }

    fun getFriendUpdates(): Single<Feed> = goodreadsApiDefinition.getFriendUpdates()
            .map { ModelMapper.mapFeedApiToView(it.updates) }
}