package com.thunderclouddev.tirforgoodreads

import android.app.Application
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
import com.thunderclouddev.tirforgoodreads.api.GoodreadsApiBuilder
import com.thunderclouddev.tirforgoodreads.database.Data
import com.thunderclouddev.tirforgoodreads.database.RequeryDatabase
import com.thunderclouddev.tirforgoodreads.logging.timber.Timber
import io.victoralbertos.jolyglot.GsonSpeaker
import org.fuckboilerplate.rx_social_connect.RxSocialConnect



/**
 * @author David Whitman on 11 Mar, 2017.
 */
class BaseApp : Application() {
    companion object {
        lateinit var data: Data
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

        val goodreadsApi = GoodreadsApiBuilder().goodreadsApi
        val database = RequeryDatabase(this)
        data = Data(goodreadsApi, database)
    }
}