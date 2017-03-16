package com.thunderclouddev.tirforgoodreads.viewbooks

import android.databinding.DataBindingUtil
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bluelinelabs.conductor.Controller
import com.thunderclouddev.tirforgoodreads.BaseApp
import com.thunderclouddev.tirforgoodreads.R
import com.thunderclouddev.tirforgoodreads.databinding.ViewBooksBinding
import com.thunderclouddev.tirforgoodreads.logging.timberkt.TimberKt
import com.uber.autodispose.CompletableScoper
import com.uber.autodispose.ObservableScoper
import com.uber.autodispose.android.ViewScopeProvider
import io.reactivex.android.schedulers.AndroidSchedulers
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

    init {
        addLifecycleListener(object : LifecycleListener() {
            override fun postAttach(controller: Controller, view: View) {
                super.postAttach(controller, view)
                subscribeToDatabase(booksAdapter)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        binding = DataBindingUtil.inflate<ViewBooksBinding>(inflater, R.layout.view_books, container, false)
        binding.bookList.layoutManager = LinearLayoutManager(activity)
        binding.booksRefreshLayout.setOnRefreshListener { refresh() }

        booksAdapter = ViewBooksAdapter(activity!!)
        binding.bookList.adapter = booksAdapter

        binding.getFeed.setOnClickListener {
            trySignIn()
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

        RxSocialConnect.with(activity, BaseApp.goodreadsService)
                .subscribe({ response ->
                    Toast.makeText(response.targetUI(), response.token().token, Toast.LENGTH_LONG).show()
                    TimberKt.d { response.token().token }
                }, { error ->
                    TimberKt.e(error, { "Couldn't get token!" })
                })
    }

    fun refresh() {
        fetchBooksByAuthor("18541")
                .doOnSubscribe { binding.booksRefreshLayout.isRefreshing = true }
                .doOnTerminate { binding.booksRefreshLayout.isRefreshing = false }
                .to(CompletableScoper(ViewScopeProvider.from(binding.root)))
                .subscribe({}, { error ->
                })
    }

    private fun subscribeToDatabase(booksAdapter: ViewBooksAdapter) {
        BaseApp.data.createBooksByAuthorFromDatabaseObservable("18541")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .to(ObservableScoper(ViewScopeProvider.from(binding.root)))
                .subscribe({ book ->
                    TimberKt.v { "Reading book from db: $book" }
                    booksAdapter.edit().add(ViewBooksAdapter.BookViewModel(book)).commit()
                }, { error ->
                    throw error
                })
    }

    private fun fetchBooksByAuthor(authorId: String) = BaseApp.data.queryBooksByAuthorFromApi(authorId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}