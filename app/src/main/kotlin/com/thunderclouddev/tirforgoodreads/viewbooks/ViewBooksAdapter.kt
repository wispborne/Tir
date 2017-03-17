package com.thunderclouddev.tirforgoodreads.viewbooks

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.thunderclouddev.tirforgoodreads.BaseRecyclerViewAdapter
import com.thunderclouddev.tirforgoodreads.R
import com.thunderclouddev.tirforgoodreads.SortedListAdapter
import com.thunderclouddev.tirforgoodreads.databinding.BookItemBinding
import com.thunderclouddev.tirforgoodreads.getOrDefaultIfNullOrBlank
import com.thunderclouddev.goodreadsapisdk.model.Book

/**
 * @author David Whitman on 11 Mar, 2017.
 */
class ViewBooksAdapter(private val context: Context)
    : SortedListAdapter<ViewBooksAdapter.BookViewModel>(BookViewModel::class.java, BookViewModel.DefaultComparator()) {

    override fun areItemsTheSame(item1: BookViewModel, item2: BookViewModel) = item1.book.id == item2.book.id

    override fun areItemContentsTheSame(oldItem: BookViewModel, newItem: BookViewModel) = oldItem.book == newItem.book

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int) =
            ViewHolder(DataBindingUtil.inflate<BookItemBinding>(layoutInflator, R.layout.book_item, parent, false))


    private val layoutInflator: LayoutInflater by lazy { LayoutInflater.from(context) }

    override fun getItemCount() = items().size


    data class BookViewModel(val book: Book) : BaseRecyclerViewAdapter.ViewModel {
        val title = book.titleWithoutSeries.getOrDefaultIfNullOrBlank(book.title)
        val authors = book.authors.joinToString { it.name }
        val imageUrl = book.imageUrl

        class DefaultComparator : Comparator<BookViewModel> {
            override fun compare(left: BookViewModel, right: BookViewModel): Int {
                return left.book.title.compareTo(right.book.title) // TODO: Handle when this is blank
            }
        }
    }

    data class ViewHolder(private val binding: BookItemBinding)
        : BaseRecyclerViewAdapter.ViewHolder<BookViewModel>(binding) {
        override fun performBind(item: BookViewModel) {
            Picasso.with(binding.root.context)
                    .load(item.imageUrl)
                    .into(binding.bookCover)

            binding.book = item
        }
    }
}