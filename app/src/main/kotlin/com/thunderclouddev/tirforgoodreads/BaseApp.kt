package com.thunderclouddev.tirforgoodreads

import android.app.Application
import com.thunderclouddev.tirforgoodreads.api.GoodreadsApi
import com.thunderclouddev.tirforgoodreads.api.GoodreadsApiBuilder

/**
 * @author David Whitman on 11 Mar, 2017.
 */
class BaseApp : Application() {
    companion object {
        lateinit var goodreadsApi: GoodreadsApi
    }

    override fun onCreate() {
        super.onCreate()

        goodreadsApi = GoodreadsApiBuilder().goodreadsApi
    }
}