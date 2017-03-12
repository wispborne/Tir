package com.thunderclouddev.tirforgoodreads.viewbooks

import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.thunderclouddev.tirforgoodreads.BaseApp
import com.thunderclouddev.tirforgoodreads.R
import com.thunderclouddev.tirforgoodreads.databinding.ViewBooksBinding
import com.thunderclouddev.tirforgoodreads.logging.timberkt.TimberKt
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by David Whitman on 11 Mar, 2017.
 */
class ViewBooksController : Controller() {
    private lateinit var binding: ViewBooksBinding
    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        binding = DataBindingUtil.inflate<ViewBooksBinding>(inflater, R.layout.view_books, container, false)
        binding.bookList.layoutManager = LinearLayoutManager(activity)
        binding.booksRefreshLayout.setOnRefreshListener { refresh() }

        val booksAdapter = ViewBooksAdapter(activity!!)
        binding.bookList.adapter = booksAdapter

        disposables.add(BaseApp.data.booksByAuthor("23423")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ book ->
                    TimberKt.d { "Reading book from db: $book" }
                    booksAdapter.edit().add(ViewBooksAdapter.BookViewModel(book)).commit()
                }, { error ->
                    throw error
                }))

        return binding.root
    }

    fun refresh() {
        disposables.add(
                fetchBooksByAuthor("23423")
                        .doOnSubscribe { binding.booksRefreshLayout.isRefreshing = true }
                        .doOnTerminate { binding.booksRefreshLayout.isRefreshing = false }
                        .subscribe())
    }

    override fun onDestroyView(view: View) {
        disposables.dispose()
        super.onDestroyView(view)
    }

    // TODO("Split into two methods")
    private fun fetchBooksByAuthor(authorId: String) = BaseApp.data.fetchBooksByAuthorFromApi(authorId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}