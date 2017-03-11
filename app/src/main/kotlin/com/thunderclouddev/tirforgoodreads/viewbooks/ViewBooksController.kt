package com.thunderclouddev.tirforgoodreads.viewbooks

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.thunderclouddev.tirforgoodreads.BaseApp
import com.thunderclouddev.tirforgoodreads.R
import com.thunderclouddev.tirforgoodreads.api.model.Book
import com.thunderclouddev.tirforgoodreads.mapToViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by David Whitman on 11 Mar, 2017.
 */
class ViewBooksController : Controller() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.view_books, container)
        val recyclerView = view.findViewById(R.id.bookList) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)

        BaseApp.goodreadsApi.getAuthorBooks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    recyclerView.adapter = ViewBooksAdapter(activity!!, response.author.books.items.map(Book::mapToViewModel))
//                    AlertDialog.Builder(activity)
//                            .setMessage("Books by ${response.author.name}\n\n${response.author.books.items.joinToString(separator = "\n")}")
//                            .show()
                }

        return view
    }
}