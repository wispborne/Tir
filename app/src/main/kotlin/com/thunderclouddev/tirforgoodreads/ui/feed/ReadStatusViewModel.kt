package com.thunderclouddev.tirforgoodreads.ui.feed

import android.databinding.ObservableField
import android.text.Html
import android.text.Spanned
import com.thunderclouddev.goodreadsapisdk.model.feed.ReadStatusFeedItem
import com.thunderclouddev.tirforgoodreads.BaseRecyclerViewAdapter
import com.thunderclouddev.tirforgoodreads.isUri
import com.thunderclouddev.tirforgoodreads.ui.ViewModel
import com.thunderclouddev.tirforgoodreads.uri.Uri
import java.util.*

/**
 * @author David Whitman on 18 Mar, 2017.
 */
class ReadStatusViewModel(private val readStatusFeedItem: ReadStatusFeedItem) : ViewModel, BaseRecyclerViewAdapter.ViewModel {
    val userUri = ObservableField<Uri>()
    val status = ObservableField<Spanned>(Html.fromHtml(readStatusFeedItem.actionText))
    val imageUrl = ObservableField<Uri>(Uri.parse(readStatusFeedItem.imageUrl))

    override fun onCreate() {
        if (readStatusFeedItem.imageUrl.isUri()) {
            userUri.set(Uri.parse(readStatusFeedItem.imageUrl))
        }
    }

    object DefaultComparator : Comparator<ReadStatusViewModel> {
        override fun compare(left: ReadStatusViewModel, right: ReadStatusViewModel)
                = left.readStatusFeedItem.updatedAt.compareTo(right.readStatusFeedItem.updatedAt)
    }
}