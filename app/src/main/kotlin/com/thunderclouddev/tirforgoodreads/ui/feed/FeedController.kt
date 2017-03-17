package com.thunderclouddev.tirforgoodreads.ui.feed

import android.databinding.DataBindingUtil
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bluelinelabs.conductor.Controller
import com.thunderclouddev.goodreadsapisdk.model.feed.ReadStatusFeedItem
import com.thunderclouddev.tirforgoodreads.BaseApp
import com.thunderclouddev.tirforgoodreads.R
import com.thunderclouddev.tirforgoodreads.databinding.FeedViewBinding
import com.thunderclouddev.tirforgoodreads.logging.timberkt.TimberKt
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.fuckboilerplate.rx_social_connect.RxSocialConnect
import org.fuckboilerplate.rx_social_connect.query_string.QueryString
import org.fuckboilerplate.rx_social_connect.query_string.QueryStringStrategy

/**
 * @author David Whitman on 15 Mar, 2017.
 */
class FeedController : Controller() {
    private lateinit var binding: FeedViewBinding
    private lateinit var feedAdapter: FeedAdapter

    init {
        addLifecycleListener(object : LifecycleListener() {
            override fun postAttach(controller: Controller, view: View) {
                super.postAttach(controller, view)
                refresh()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        binding = DataBindingUtil.inflate<FeedViewBinding>(inflater, R.layout.feed_view, container, false)

        binding.feedRecyclerView.layoutManager = LinearLayoutManager(activity)
        feedAdapter = FeedAdapter()
        binding.feedRecyclerView.adapter = feedAdapter

        binding.feedRefreshLayout.setOnRefreshListener {
            refresh()
        }

        return binding.root // for now
    }

    private fun refresh() {
        getFeed()
    }

    private fun getFeed() {
        QueryString.PARSER.replaceStrategyOAuth1(object : QueryStringStrategy {
            override fun extractCode(uri: Uri): String {
                return uri.getQueryParameter("oauth_token")
            }

            override fun extractError(uri: Uri): String? {
                return uri.getQueryParameter("error")
            }

        })

        RxSocialConnect.with(activity, BaseApp.goodreadsOAuthService)
                .singleOrError()
                .doAfterSuccess { response ->
                    Toast.makeText(response.targetUI(), response.token().token, Toast.LENGTH_LONG).show()
                    TimberKt.d { response.token().token }
                }
                .flatMap {
                    BaseApp.data.queryFriendUpdates()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
                .doAfterTerminate { binding.feedRefreshLayout.isRefreshing = false }
                .subscribe({ author ->
                    TimberKt.d { author.toString() }
                    feedAdapter.edit().replaceAll(author.items
                            .filterIsInstance(ReadStatusFeedItem::class.java)
                            .map(::ReadStatusViewModel))
                            .commit()
                }, { error ->
                    TimberKt.e(error, { error.toString() })
                })
    }
}