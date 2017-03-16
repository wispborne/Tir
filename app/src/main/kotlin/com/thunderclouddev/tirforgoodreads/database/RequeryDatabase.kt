package com.thunderclouddev.tirforgoodreads.database

import android.content.Context
import com.thunderclouddev.tirforgoodreads.BuildConfig
import com.thunderclouddev.tirforgoodreads.database.model.AuthorEntity
import com.thunderclouddev.tirforgoodreads.database.model.BookEntity
import com.thunderclouddev.tirforgoodreads.database.model.BookEntity_AuthorEntity
import com.thunderclouddev.tirforgoodreads.database.model.Models
import io.reactivex.Completable
import io.reactivex.Observable
import io.requery.Persistable
import io.requery.android.sqlite.DatabaseSource
import io.requery.reactivex.KotlinReactiveEntityStore
import io.requery.sql.KotlinEntityDataStore
import io.requery.sql.TableCreationMode

/**
 * @author David Whitman on 12 Mar, 2017.
 */
class RequeryDatabase(context: Context) {
    private val data: KotlinReactiveEntityStore<Persistable> by lazy {
        // override onUpgrade to handle migrating to a new version
        val source = DatabaseSource(context, Models.DEFAULT, 2)

        if (BuildConfig.DEBUG) {
            // use this in development mode to drop and recreate the tables on every upgrade
            source.setTableCreationMode(TableCreationMode.DROP_CREATE)
        }

        KotlinReactiveEntityStore(KotlinEntityDataStore<Persistable>(source.configuration))
    }

    fun getBooksByAuthor(authorId: String): Observable<BookEntity> = data.select(BookEntity::class)
            .where(BookEntity.ID.`in`(bookIdsByAuthor(authorId)))
            .get()
            .observableResult().flatMap { it.observable() }

    fun putBooks(books: List<BookEntity>): Completable = data.upsert(books).toCompletable()

    fun putAuthors(authors: List<AuthorEntity>) = data.upsert(authors)

    private fun bookIdsByAuthor(authorId: String) = data
            .select(BookEntity_AuthorEntity::class)
            .where(BookEntity_AuthorEntity.AUTHOR_ID.eq(authorId))
            .get().map { it.bookId }.toList()
}