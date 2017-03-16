package com.thunderclouddev.tirforgoodreads.database

import com.thunderclouddev.goodreadsapisdk.GoodreadsApi
import com.thunderclouddev.tirforgoodreads.database.model.AuthorEntity
import com.thunderclouddev.tirforgoodreads.database.model.BookEntity
import com.thunderclouddev.tirforgoodreads.empty
import com.thunderclouddev.tirforgoodreads.logging.timberkt.TimberKt
import com.thunderclouddev.tirforgoodreads.model.Author
import com.thunderclouddev.tirforgoodreads.model.Book
import io.reactivex.Completable
import io.reactivex.Observable
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * Main data source, which manages caching.
 * @author David Whitman on 12 Mar, 2017.
 */
class Data(private val api: GoodreadsApi, private val database: RequeryDatabase) {

    fun createBooksByAuthorFromDatabaseObservable(authorId: String): Observable<Book> {
        return getBooksByAuthorFromDatabase(authorId)
                .map { mapBookDatabaseToView(it) }
    }

    fun queryBooksByAuthorFromApi(authorId: String): Completable = api.getAuthorBooks(authorId)
            .doOnSuccess { (_, author) ->
                cacheBooks(author.books.items.map { mapBooksApiToView(it) })
                        .subscribe({ }, { error ->
                            TimberKt.e(error, { error.message ?: String.empty })
                        })
            }
            .toCompletable()

    fun queryFriendUpdates() = api.getFriendUpdates()

    private fun mapBookDatabaseToView(databaseBook: BookEntity) = Book(id = databaseBook.id,
            isbn = databaseBook.isbn,
            isbn13 = databaseBook.isbn13,
            title = databaseBook.title,
            reviewsCount = databaseBook.reviewsCount,
            titleWithoutSeries = databaseBook.titleWithoutSeries,
            imageUrl = databaseBook.imageUrl,
            smallImageUrl = databaseBook.smallImageUrl,
            largeImageUrl = databaseBook.largeImageUrl,
            link = databaseBook.link,
            numPages = databaseBook.numPages,
            format = databaseBook.format,
            editionInformation = databaseBook.editionInformation,
            publisher = databaseBook.publisher,
            publicationDay = databaseBook.publicationDay,
            publicationYear = databaseBook.publicationYear,
            publicationMonth = databaseBook.publicationMonth,
            averageRating = databaseBook.averageRating,
            ratingsCount = databaseBook.ratingsCount,
            description = databaseBook.description,
            published = databaseBook.published,
            authors = databaseBook.authors.map { mapAuthorsDatabaseToView(it as AuthorEntity) })

    private fun cacheBooks(books: List<Book>) =
            database.putBooks(books
                    .map {
                        val databaseEntity = mapBookViewToDatabase(it)
                        // Cache authors
                        database.putAuthors(databaseEntity.second).subscribe({ addedAuthors ->
                            TimberKt.v { "Put $addedAuthors" }
                        }, { error ->
                            TimberKt.e(error, { "Error putting $error" })
                        })
                        databaseEntity.first
                    })

    private fun mapAuthorsDatabaseToView(databaseAuthor: AuthorEntity) = Author(
            id = databaseAuthor.id,
            name = databaseAuthor.name,
            link = databaseAuthor.link,
            imageUrl = databaseAuthor.imageUrl,
            role = databaseAuthor.role,
            smallImageUrl = databaseAuthor.smallImageUrl,
            averageRating = databaseAuthor.averageRating,
            ratingsCount = databaseAuthor.ratingsCount,
            textReviewsCount = databaseAuthor.textReviewsCount
    )

    private fun getBooksByAuthorFromDatabase(authorId: String) = database.getBooksByAuthor(authorId)

    private fun mapBooksApiToView(apiBook: com.thunderclouddev.goodreadsapisdk.Book): Book {
        return Book(id = apiBook.id,
                isbn = apiBook.isbn,
                isbn13 = apiBook.isbn13,
                title = apiBook.title,
                reviewsCount = apiBook.reviewsCount,
                titleWithoutSeries = apiBook.titleWithoutSeries,
                imageUrl = apiBook.imageUrl,
                smallImageUrl = apiBook.smallImageUrl,
                largeImageUrl = apiBook.largeImageUrl,
                link = apiBook.link,
                numPages = apiBook.numPages,
                format = apiBook.format,
                editionInformation = apiBook.editionInformation,
                publisher = apiBook.publisher,
                publicationDay = apiBook.publicationDay,
                publicationYear = apiBook.publicationYear,
                publicationMonth = apiBook.publicationMonth,
                averageRating = apiBook.averageRating,
                ratingsCount = apiBook.ratingsCount,
                description = apiBook.description,
                published = apiBook.published,
                authors = mapAuthorsApiToView(apiBook.authors.items))
    }

    private fun mapAuthorsApiToView(apiAuthors: ArrayList<com.thunderclouddev.goodreadsapisdk.Author>): List<Author> {
        return apiAuthors
                .map { author ->
                    Author(id = author.id,
                            name = author.name,
                            link = author.link,
                            imageUrl = author.imageUrl,
                            role = author.role,
                            smallImageUrl = author.smallImageUrl,
                            averageRating = author.averageRating,
                            ratingsCount = author.ratingsCount,
                            textReviewsCount = author.textReviewsCount)
                }
    }

    private fun mapAuthorViewToDatabase(author: Author): AuthorEntity {
        return AuthorEntity().apply {
            setId(author.id)
            setName(author.name)
            setLink(author.link)
            setImageUrl(author.imageUrl)
            setRole(author.role)
            setSmallImageUrl(author.smallImageUrl)
            setAverageRating(author.averageRating)
            setRatingsCount(author.ratingsCount)
            setTextReviewsCount(author.textReviewsCount)
            setDateTimeCached(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
        }
    }

    private fun mapBookViewToDatabase(book: Book): Pair<BookEntity, List<AuthorEntity>> {
        return Pair(BookEntity().apply {
            setId(book.id)
            setIsbn(book.isbn)
            setIsbn13(book.isbn13)
            setTitle(book.title)
            setReviewsCount(book.reviewsCount)
            setTitleWithoutSeries(book.titleWithoutSeries)
            setImageUrl(book.imageUrl)
            setSmallImageUrl(book.smallImageUrl)
            setLargeImageUrl(book.largeImageUrl)
            setLink(book.link)
            setNumPages(book.numPages)
            setFormat(book.format)
            setEditionInformation(book.editionInformation)
            setPublisher(book.publisher)
            setPublicationDay(book.publicationDay)
            setPublicationYear(book.publicationYear)
            setPublicationMonth(book.publicationMonth)
            setAverageRating(book.averageRating)
            setRatingsCount(book.ratingsCount)
            setDescription(book.description)
            setPublished(book.published)
            book.authors.map { mapAuthorViewToDatabase(it) }.forEach { this.authors.add(it) }
            setDateTimeCached(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
        }, book.authors.map { mapAuthorViewToDatabase(it) })
    }
}