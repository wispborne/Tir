package com.thunderclouddev.tirforgoodreads.ui.feed

import android.databinding.ObservableField
import com.thunderclouddev.goodreadsapisdk.model.feed.ReadStatus
import com.thunderclouddev.goodreadsapisdk.model.feed.ReadStatusFeedItem
import com.thunderclouddev.tirforgoodreads.ui.BaseRecyclerViewAdapter
import com.thunderclouddev.tirforgoodreads.R
import com.thunderclouddev.tirforgoodreads.isUri
import com.thunderclouddev.tirforgoodreads.ui.StringResource
import com.thunderclouddev.tirforgoodreads.ui.ViewModel
import com.thunderclouddev.tirforgoodreads.uri.Uri

/**
 * @author David Whitman on 18 Mar, 2017.
 */
class ReadStatusViewModel(private val readStatus: ReadStatusFeedItem, private val stringResource: StringResource) : ViewModel, BaseRecyclerViewAdapter.ViewModel {
    val userUri = ObservableField<Uri>()
    val status = ObservableField<String>()
    val profileImageUrl = ObservableField<Uri>(Uri.parse(readStatus.imageUrl))
    val bookCoverImageUrl = ObservableField<Uri>(Uri.parse(readStatus.obj.review.book.imageUrl))
    val timeStamp = ObservableField<Long>(readStatus.updatedAt.toEpochMilli())
    val bookTitle = ObservableField<String>(readStatus.obj.review.book.title)
    val author = ObservableField<String>(stringResource.getString(R.string.book_authorBy, readStatus.obj.review.book.authors.map { it.name }.joinToString()))

    init {
        if (readStatus.imageUrl.isUri()) {
            userUri.set(Uri.parse(readStatus.imageUrl))
        }

        val statusText = when (readStatus.obj.status) {
            ReadStatus.Status.IsReading -> stringResource.getString(R.string.update_status_currentlyReading, readStatus.actor.name)
            ReadStatus.Status.ToRead -> stringResource.getString(R.string.update_status_wantsToRead, readStatus.actor.name)
            ReadStatus.Status.Read -> stringResource.getString(R.string.update_status_finishedReading, readStatus.actor.name)
            ReadStatus.Status.CustomShelf -> stringResource.getString(R.string.update_status_changedShelf, readStatus.actor.name, readStatus.obj.status.shelf)
        }

        status.set(statusText)
    }

    override fun onCreate() {
    }

    fun onStart() {
        timeStamp.set(readStatus.updatedAt.toEpochMilli())
    }

    object DefaultComparator : Comparator <ReadStatusViewModel> {
        override fun compare(left: ReadStatusViewModel, right: ReadStatusViewModel)
                = right.readStatus.updatedAt.compareTo(left.readStatus.updatedAt)
    }
}