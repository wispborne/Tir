package com.thunderclouddev.goodreadsapisdk

import com.thunderclouddev.goodreadsapisdk.api.GoodreadsApiDefinition
import com.thunderclouddev.goodreadsapisdk.model.Book
import com.thunderclouddev.goodreadsapisdk.model.feed.Feed
import com.thunderclouddev.goodreadsapisdk.model.feed.FeedItem
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

    /**
     * The parameters don't work. Api problem.
     */
    fun getFriendUpdates(updateType: FeedItem.Type? = null,
                         updateFilter: UpdateFilter? = null,
                         take: Int? = null): Single<Feed> = goodreadsApiDefinition.getFriendUpdates(mapOf(
            Pair("update", when (updateType) {
                FeedItem.Type.ReadStatus -> "statuses"
                FeedItem.Type.Book -> "books"
                FeedItem.Type.Review -> "reviews"
                else -> ""
            }),
            Pair("update_filter", when (updateFilter) {
                UpdateFilter.Following -> "following"
                UpdateFilter.TopFriends -> "top_friends"
                UpdateFilter.Friends -> "friends"
                else -> ""
            }),
            Pair("max_updates", take?.toString() ?: "")
    ))
            .map { ModelMapper.mapFeedApiToView(it.updates) }


    fun getFriendUpdatesV3(updateType: FeedItem.Type? = null,
                           updateFilter: UpdateFilter? = null,
                           take: Int? = null,
                           nonSocialOnly: Boolean = false,
                           skipCache: Boolean = true,
                           largeBookCovers: Boolean = true,
                           previewInfo: Boolean = true,
                           buyLinks: Boolean = false): Single<Feed> = goodreadsApiDefinition.getFriendUpdatesV3(mapOf(
            Pair("update", when (updateType) {
                FeedItem.Type.ReadStatus -> "statuses"
                FeedItem.Type.Book -> "books"
                FeedItem.Type.Review -> "reviews"
                else -> ""
            }),
            Pair("update_filter", when (updateFilter) {
                UpdateFilter.Following -> "following"
                UpdateFilter.TopFriends -> "top_friends"
                UpdateFilter.Friends -> "friends"
                else -> ""
            }),
            Pair("max_updates", take?.toString() ?: ""),
            Pair("nonsocial_only", nonSocialOnly.toString()),
            Pair("skip_cache", skipCache.toString()),
            Pair("_extras[book_covers_large]", largeBookCovers.toString()),
            Pair("_extras[preview_info]", previewInfo.toString()),
            Pair("_extras[buy_links]", buyLinks.toString())
    ))
            .map { ModelMapper.mapFeedApiToView(it.updates) }

    enum class UpdateFilter {
        Friends,
        Following,
        TopFriends
    }
}