package com.thunderclouddev.tirforgoodreads.database

import com.thunderclouddev.goodreadsapisdk.GoodreadsApi
import com.thunderclouddev.goodreadsapisdk.model.Book
import com.thunderclouddev.goodreadsapisdk.model.feed.Feed
import com.thunderclouddev.tirforgoodreads.empty
import com.thunderclouddev.tirforgoodreads.logging.timberkt.TimberKt
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Main data source, which manages caching.
 * @author David Whitman on 12 Mar, 2017.
 */
class Data(private val apiDefinition: GoodreadsApi, private val database: RequeryDatabase) {

    fun createBooksByAuthorFromDatabaseObservable(authorId: String): Observable<Book> {
        return database.getBooksByAuthor(authorId)
                .map { DatabaseModelMapper.mapBookDatabaseToView(it) }
    }

    fun queryBooksByAuthorFromApi(authorId: String): Completable = apiDefinition.getAuthorBooks(authorId)
            .doOnSuccess { books ->
                cacheBooks(books)
                        .subscribe({ }, { error ->
                            TimberKt.e(error, { error.message ?: String.empty })
                        })
            }
            .toCompletable()

    fun queryFriendUpdates(): Single<Feed> = apiDefinition.getFriendUpdates()
            .doOnSuccess {
                // Cache results
            }

    private fun cacheBooks(books: List<Book>) =
            database.putBooks(books
                    .map {
                        val databaseEntity = DatabaseModelMapper.mapBookViewToDatabase(it)
                        // Cache authors
                        database.putAuthors(databaseEntity.second).subscribe({ addedAuthors ->
                            TimberKt.v { "Put $addedAuthors" }
                        }, { error ->
                            TimberKt.e(error, { "Error putting $error" })
                        })
                        databaseEntity.first
                    })
}