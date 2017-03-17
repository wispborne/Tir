package com.thunderclouddev.goodreadsapisdk

import com.thunderclouddev.goodreadsapisdk.api.Actor
import com.thunderclouddev.goodreadsapisdk.api.Update
import com.thunderclouddev.goodreadsapisdk.api.UpdateObject
import com.thunderclouddev.goodreadsapisdk.api.Updates
import com.thunderclouddev.goodreadsapisdk.model.Author
import com.thunderclouddev.goodreadsapisdk.model.Book
import com.thunderclouddev.goodreadsapisdk.model.Review
import com.thunderclouddev.goodreadsapisdk.model.feed.Feed
import com.thunderclouddev.goodreadsapisdk.model.feed.ReadStatus
import com.thunderclouddev.goodreadsapisdk.model.feed.ReadStatusFeedItem
import com.thunderclouddev.goodreadsapisdk.model.feed.ReviewFeedItem
import org.threeten.bp.Instant
import java.lang.RuntimeException

/**
 * @author David Whitman on 17 Mar, 2017.
 */
internal object ModelMapper {
    internal fun mapBookApiToView(apiBook: com.thunderclouddev.goodreadsapisdk.api.Book): Book {
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

    internal fun mapAuthorsApiToView(apiAuthors: ArrayList<com.thunderclouddev.goodreadsapisdk.api.Author>): List<Author> {
        return apiAuthors
                .map { (id, name, link, imageUrl, role, smallImageUrl, averageRating, ratingsCount, textReviewsCount) ->
                    Author(id = id,
                            name = name,
                            link = link,
                            imageUrl = imageUrl,
                            role = role,
                            smallImageUrl = smallImageUrl,
                            averageRating = averageRating,
                            ratingsCount = ratingsCount,
                            textReviewsCount = textReviewsCount)
                }
    }

    internal fun mapFeedApiToView(updates: Updates) = Feed(
            updates.updates.map { update ->
                when (update.type) {
                    "review" -> mapReviewFeedItemApiToView(update)
                    "readstatus" -> mapReadStatusFeedItemApiToView(update)
                    else -> throw RuntimeException("${update.type} not mapped!")
                }

            }
    )

    internal fun mapReadStatusFeedItemApiToView(update: Update) = ReadStatusFeedItem(
            update.actionText,
            update.link,
            update.imageUrl,
            mapActorApiToView(update.actor),
            update.updatedAt.toInstant(defaultValue = Instant.MIN),
            mapReadStatusApiToView(update.updateObject)
    )

    internal fun mapReviewFeedItemApiToView(update: Update) = ReviewFeedItem(
            update.actionText,
            update.link,
            update.imageUrl,
            mapActorApiToView(update.actor),
            update.updatedAt.toInstant(defaultValue = Instant.MIN),
            mapBookApiToView(update.updateObject.book)
    )

    internal fun mapReadStatusApiToView(updateObject: UpdateObject) = ReadStatus(
            reviewId = updateObject.readStatus.reviewId,
            userId = updateObject.readStatus.userId,
            oldStatus = updateObject.readStatus.oldStatus,
            status = updateObject.readStatus.status,
            updatedAt = updateObject.readStatus.updatedAt,
            review = mapReviewApiToView(updateObject.readStatus.review)
    )

    internal fun mapReviewApiToView(review: com.thunderclouddev.goodreadsapisdk.api.Review) = Review(
            id = review.id,
            userId = review.userId,
            bookId = review.bookId,
            rating = review.rating,
            readStatus = review.readStatus,
            sellFlag = review.sellFlag,
            review = review.review,
            recommendation = review.recommendation,
            readAt = review.readAt,
            updatedAt = review.updatedAt,
            createdAt = review.createdAt,
            commentsCount = review.commentsCount,
            weight = review.weight,
            ratingsSum = review.ratingsSum,
            ratingsCount = review.ratingsCount,
            notes = review.notes,
            spoilerFlag = review.spoilerFlag,
            recommenderUserId1 = review.recommenderUserId1,
            recommenderUserName1 = review.recommenderUserName1,
            workId = review.workId,
            readCount = review.readCount,
            lastCommentAt = review.lastCommentAt,
            startedAt = review.startedAt,
            hiddenFlag = review.hiddenFlag,
            languageCode = review.languageCode,
            lastRevisionAt = review.lastRevisionAt,
            nonFriendsRatingCount = review.nonFriendsRatingCount,
            book = mapBookApiToView(review.book)
    )

    internal fun mapActorApiToView(actor: Actor) = com.thunderclouddev.goodreadsapisdk.model.Actor(
            id = actor.id,
            name = actor.name,
            imageUrl = actor.image_url,
            link = actor.link
    )
}