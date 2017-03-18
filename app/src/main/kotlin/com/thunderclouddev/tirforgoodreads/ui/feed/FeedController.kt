package com.thunderclouddev.tirforgoodreads.ui.feed

import android.app.Activity
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
import com.thunderclouddev.tirforgoodreads.ui.StringResource
import com.uber.autodispose.SingleScoper
import com.uber.autodispose.android.ViewScopeProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.fuckboilerplate.rx_social_connect.RxSocialConnect
import org.fuckboilerplate.rx_social_connect.query_string.QueryString
import org.fuckboilerplate.rx_social_connect.query_string.QueryStringStrategy

/**
 * @author David Whitman on 15 Mar, 2017.
 */
class FeedController : Controller() {
    private val SIGNIN_LAYOUT_INDEX = 0
    private val FEED_LAYOUT_INDEX = 1

    private lateinit var binding: FeedViewBinding
    private lateinit var feedAdapter: FeedAdapter

    init {
        addLifecycleListener(object : LifecycleListener() {
            override fun postAttach(controller: Controller, view: View) {
                super.postAttach(controller, view)
                onPostAttach()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        binding = DataBindingUtil.inflate<FeedViewBinding>(inflater, R.layout.feed_view, container, false)
        initFeedUi()
        return binding.root
    }

    private fun onPostAttach() {
        var hasToken = false

        try {
            RxSocialConnect.getTokenOAuth1(BaseApp.goodreadsOAuthService.api.javaClass)
                    .blockingFirst()
            hasToken = true
        } catch (ignored: Exception) {
        }

        TimberKt.v { "Found token: $hasToken" }

        if (hasToken) {
            binding.feedSwitcher.displayedChild = FEED_LAYOUT_INDEX
            refresh()
        } else {
            binding.feedSwitcher.displayedChild = SIGNIN_LAYOUT_INDEX
            binding.feedSignInButton.setOnClickListener {
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
                        .to(SingleScoper(ViewScopeProvider.from(binding.root)))
                        .subscribe { response ->
                            Toast.makeText(binding.root.context, "Sign in successful", Toast.LENGTH_SHORT).show()
                            TimberKt.d { "Acquired token: ${response.token().token}" }
                            binding.feedSwitcher.displayedChild = FEED_LAYOUT_INDEX
                            refresh()
                        }
            }
        }

        feedAdapter.onCreate()
    }

    override fun onActivityStarted(activity: Activity) {
        super.onActivityStarted(activity)
        feedAdapter.onStart()
    }

    private fun initFeedUi() {
        binding.feedRecyclerView.layoutManager = LinearLayoutManager(activity)
        feedAdapter = FeedAdapter()
        binding.feedRecyclerView.adapter = feedAdapter
        binding.feedRefreshLayout.setOnRefreshListener { refresh() }
    }

    private fun refresh() {
        getFeed()
    }

    private fun getFeed() {
        val stringResource = StringResource(binding.root.resources)

        BaseApp.data.queryFriendUpdates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { binding.feedRefreshLayout.isRefreshing = false }
                .to(SingleScoper(ViewScopeProvider.from(binding.root)))
                .subscribe({ author ->
                    TimberKt.d { author.toString() }
                    feedAdapter.edit().replaceAll(author.items
                            .filterIsInstance(ReadStatusFeedItem::class.java)
                            .map { ReadStatusViewModel(it, stringResource) })
                            .commit()
                }, { error ->
                    TimberKt.e(error, { error.toString() })
                })
    }
}