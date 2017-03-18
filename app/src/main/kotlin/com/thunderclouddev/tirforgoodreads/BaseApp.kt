package com.thunderclouddev.tirforgoodreads

import android.app.Application
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.scribejava.core.oauth.OAuth10aService
import com.jakewharton.threetenabp.AndroidThreeTen
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.leakcanary.LeakCanary
import com.thunderclouddev.goodreadsapisdk.GoodreadsApi
import com.thunderclouddev.goodreadsapisdk.api.GoodreadsOAuthApi
import com.thunderclouddev.tirforgoodreads.api.OAuth1Interceptor
import com.thunderclouddev.tirforgoodreads.database.Data
import com.thunderclouddev.tirforgoodreads.database.RequeryDatabase
import com.thunderclouddev.tirforgoodreads.logging.timber.Timber
import io.victoralbertos.jolyglot.GsonSpeaker
import okhttp3.OkHttpClient
import org.fuckboilerplate.rx_social_connect.RxSocialConnect


/**
 * @author David Whitman on 11 Mar, 2017.
 */
class BaseApp : Application() {
    companion object {
        lateinit var data: Data
        lateinit var goodreadsOAuthService: OAuth10aService
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this) // Java 8 DateTime lib backport init
        Stetho.initializeWithDefaults(this)
        LeakCanary.install(this)
        RxSocialConnect.register(this, "GoodreadsKey")
                .using(GsonSpeaker())


        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        goodreadsOAuthService = com.github.scribejava.core.builder.ServiceBuilder()
                .apiKey("KUtQGqdhmKy1nUyQnFZRzA") // TODO move these
                .apiSecret("qlhTxMwfzA7eOeCVKNiG9BeCjwuaf6xo7F2Jjqsfzeo")
                .callback(GoodreadsOAuthApi.CALLBACK_URI)
                .build(GoodreadsOAuthApi())

        val builder = OkHttpClient.Builder()
                .addNetworkInterceptor(OAuth1Interceptor(goodreadsOAuthService))
                .addInterceptor(ChuckInterceptor(this))

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(StethoInterceptor())
        }

        val goodreadsApi = GoodreadsApi(builder.build())
        data = Data(goodreadsApi, RequeryDatabase(this))
    }
}