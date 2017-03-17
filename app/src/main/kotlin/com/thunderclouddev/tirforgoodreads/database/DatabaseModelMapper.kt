package com.thunderclouddev.tirforgoodreads.database

import com.thunderclouddev.goodreadsapisdk.model.Author
import com.thunderclouddev.goodreadsapisdk.model.Book
import com.thunderclouddev.tirforgoodreads.database.model.AuthorEntity
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * @author David Whitman on 17 Mar, 2017.
 */
object DatabaseModelMapper {
    fun mapBookDatabaseToView(databaseBook: com.thunderclouddev.tirforgoodreads.database.model.BookEntity) = Book(id = databaseBook.id,
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

    fun mapBookViewToDatabase(book: Book): Pair<com.thunderclouddev.tirforgoodreads.database.model.BookEntity, List<com.thunderclouddev.tirforgoodreads.database.model.AuthorEntity>> {
        return Pair(com.thunderclouddev.tirforgoodreads.database.model.BookEntity().apply {
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

    fun mapAuthorsDatabaseToView(databaseAuthor: com.thunderclouddev.tirforgoodreads.database.model.AuthorEntity) = Author(
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

    fun mapAuthorViewToDatabase(author: Author): com.thunderclouddev.tirforgoodreads.database.model.AuthorEntity {
        return com.thunderclouddev.tirforgoodreads.database.model.AuthorEntity().apply {
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
}