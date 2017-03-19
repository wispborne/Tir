package com.thunderclouddev.tirforgoodreads.ui

import android.app.Application
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.oauth.OAuth10aService
import com.jakewharton.threetenabp.AndroidThreeTen
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.leakcanary.LeakCanary
import com.thunderclouddev.goodreadsapisdk.GoodreadsApi
import com.thunderclouddev.goodreadsapisdk.api.GoodreadsOAuthApi
import com.thunderclouddev.goodreadsapisdk.api.GoodreadsV3OAuthApi
import com.thunderclouddev.tirforgoodreads.BuildConfig
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
        lateinit var goodreadsV3OAuthService: OAuth10aService
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

        goodreadsOAuthService = ServiceBuilder()
                .apiKey("KUtQGqdhmKy1nUyQnFZRzA") // TODO move these
                .apiSecret("qlhTxMwfzA7eOeCVKNiG9BeCjwuaf6xo7F2Jjqsfzeo")
                .callback(GoodreadsOAuthApi.CALLBACK_URI)
                .build(GoodreadsOAuthApi())

        // To get this working, need to call /oauth/grant_access_token.xml
        // with user[email] and user[password] as request text params.
        // The api will return a token
        goodreadsV3OAuthService = ServiceBuilder()
                .apiKey("==w7irqdUT3mB4Aoz/aaBXNr") // TODO move these
                .apiSecret("XvluHUeAIAa1CPlNow8tPsTfEOjvV0r2KQCbSgOh")
                .callback(GoodreadsV3OAuthApi.CALLBACK_URI)
                .build(GoodreadsV3OAuthApi())

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