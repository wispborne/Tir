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
    private lateinit var booksAdapter: ViewBooksAdapter
    private lateinit var disposables: CompositeDisposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        disposables = CompositeDisposable()
        binding = DataBindingUtil.inflate<ViewBooksBinding>(inflater, R.layout.view_books, container, false)
        binding.bookList.layoutManager = LinearLayoutManager(activity)
        binding.booksRefreshLayout.setOnRefreshListener { refresh() }

        booksAdapter = ViewBooksAdapter(activity!!)
        binding.bookList.adapter = booksAdapter

        readFromDatabase(booksAdapter)

        return binding.root
    }

    fun refresh() {
        disposables.add(
                fetchBooksByAuthor("18541")
                        .doOnSubscribe { binding.booksRefreshLayout.isRefreshing = true }
                        .doOnTerminate { binding.booksRefreshLayout.isRefreshing = false }
                        .subscribe({}, { error ->

                        }))
    }

    override fun onDestroyView(view: View) {
        disposables.dispose()
        super.onDestroyView(view)
    }

    private fun readFromDatabase(booksAdapter: ViewBooksAdapter) {
        disposables.add(BaseApp.data.createBooksByAuthorFromDatabaseObservable("18541")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ book ->
                    TimberKt.v { "Reading book from db: $book" }
                    booksAdapter.edit().add(ViewBooksAdapter.BookViewModel(book)).commit()
                }, { error ->
                    throw error
                }))
    }

    private fun fetchBooksByAuthor(authorId: String) = BaseApp.data.queryBooksByAuthorFromApi(authorId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}