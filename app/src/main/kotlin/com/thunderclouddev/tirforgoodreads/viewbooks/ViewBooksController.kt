package com.thunderclouddev.tirforgoodreads.viewbooks

import android.databinding.DataBindingUtil
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bluelinelabs.conductor.Controller
import com.github.scribejava.core.builder.ServiceBuilder
import com.thunderclouddev.tirforgoodreads.BaseApp
import com.thunderclouddev.tirforgoodreads.R
import com.thunderclouddev.tirforgoodreads.auth.GoodreadsApi
import com.thunderclouddev.tirforgoodreads.databinding.ViewBooksBinding
import com.thunderclouddev.tirforgoodreads.logging.timberkt.TimberKt
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.fuckboilerplate.rx_social_connect.RxSocialConnect
import org.fuckboilerplate.rx_social_connect.query_string.QueryString
import org.fuckboilerplate.rx_social_connect.query_string.QueryStringStrategy

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

        binding.signIn.setOnClickListener {
            trySignIn()
//            startActivity(Intent(activity!!, SignInActivity::class.java))
        }

        return binding.root
    }

    private fun trySignIn() {
        QueryString.PARSER.replaceStrategyOAuth1(object : QueryStringStrategy {
            override fun extractCode(uri: Uri): String {
                return uri.getQueryParameter("oauth_token")
            }

            override fun extractError(uri: Uri): String? {
                return uri.getQueryParameter("error")
            }

        })
        val goodReadsService = ServiceBuilder()
                .apiKey("KUtQGqdhmKy1nUyQnFZRzA")
                .apiSecret("qlhTxMwfzA7eOeCVKNiG9BeCjwuaf6xo7F2Jjqsfzeo")
                .callback(GoodreadsApi.CALLBACK_URI)
                .build(GoodreadsApi())

        RxSocialConnect.with(activity, goodReadsService)
                .subscribe({ response ->
                    Toast.makeText(response.targetUI(), response.token().token, Toast.LENGTH_LONG).show()
                    TimberKt.d { response.token().token }
                }, { error ->
                    TimberKt.e(error, { "Couldn't get token!" })
                })
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