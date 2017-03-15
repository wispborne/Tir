package com.thunderclouddev.tirforgoodreads.ui.feed

import android.databinding.DataBindingUtil
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bluelinelabs.conductor.Controller
import com.thunderclouddev.tirforgoodreads.BaseApp
import com.thunderclouddev.tirforgoodreads.R
import com.thunderclouddev.tirforgoodreads.databinding.ViewBooksBinding
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
    init {
        addLifecycleListener(object : LifecycleListener() {
            override fun postAttach(controller: Controller, view: View) {
                super.postAttach(controller, view)
                BaseApp.data.queryFriendUpdates()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ author ->
                            TimberKt.d { author.toString() }
                        }, { error ->
                            TimberKt.e(error, { error.toString() })
                        })
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val binding = DataBindingUtil.inflate<ViewBooksBinding>(inflater, R.layout.view_books, container, false)
        binding.signIn.setOnClickListener {
            trySignIn()
        }
        return binding.root // for now
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
}