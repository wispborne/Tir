package com.thunderclouddev.tirforgoodreads.viewbooks

import android.content.Context
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.thunderclouddev.tirforgoodreads.R
import com.thunderclouddev.tirforgoodreads.model.Book

/**
 * @author David Whitman on 11 Mar, 2017.
 */
class ViewBooksAdapter(private val context: Context,
                       private val items: List<Book>) : Adapter<ViewBooksAdapter.ViewHolder>() {
    private val layoutInflator: LayoutInflater by lazy { LayoutInflater.from(context) }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ViewHolder(layoutInflator.inflate(R.layout.book_item, null))


    data class ViewHolder(
            private val view: View) : android.support.v7.widget.RecyclerView.ViewHolder(view) {
        fun bind(book: Book) {
            Glide.with(view.context)
                    .load(book.imageUrl)
                    .into(view.findViewById(R.id.book_cover) as ImageView)

            (view.findViewById(R.id.book_title) as TextView).text = book.titleWithoutSeries
            (view.findViewById(R.id.book_author) as TextView).text = book.authors.joinToString { it.name }
        }
    }
}