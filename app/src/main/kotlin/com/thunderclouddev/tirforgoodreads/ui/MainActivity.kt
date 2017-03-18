package com.thunderclouddev.tirforgoodreads.ui

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.thunderclouddev.tirforgoodreads.R
import com.thunderclouddev.tirforgoodreads.R.id
import com.thunderclouddev.tirforgoodreads.R.layout
import com.thunderclouddev.tirforgoodreads.ui.ActionBarProvider
import com.thunderclouddev.tirforgoodreads.ui.feed.FeedController
import com.thunderclouddev.tirforgoodreads.ui.viewbooks.ViewBooksController

/**
 * Created by David Whitman on 11 Mar, 2017.
 */
class MainActivity : AppCompatActivity(), ActionBarProvider {
    override val actionBar: ActionBar
        get() = supportActionBar!!

    private var router: Router? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layout.main_activity)
        setSupportActionBar(findViewById(id.toolbar) as Toolbar)

        router = Conductor.attachRouter(this, findViewById(id.controller_container) as ViewGroup,
            savedInstanceState)
        if (!router!!.hasRootController()) {
            router!!.setRoot(RouterTransaction.with(FeedController()))
        }
    }

    override fun onBackPressed() {
        if (!router!!.handleBack()) {
            super.onBackPressed()
        }
    }
}