package com.thunderclouddev.tirforgoodreads.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.scribejava.core.builder.ServiceBuilder
import com.thunderclouddev.tirforgoodreads.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


/**
 * @author David Whitman on 11 Mar, 2017.
 */
class GoodreadsApiBuilder {
    val goodreadsApi: GoodreadsApi

    val goodreadsService = ServiceBuilder()
            .apiKey("KUtQGqdhmKy1nUyQnFZRzA") // TODO move these
            .apiSecret("qlhTxMwfzA7eOeCVKNiG9BeCjwuaf6xo7F2Jjqsfzeo")
            .callback(com.thunderclouddev.tirforgoodreads.auth.GoodreadsApi.CALLBACK_URI)
            .build(com.thunderclouddev.tirforgoodreads.auth.GoodreadsApi())

    init {
        val builder = OkHttpClient.Builder()
                .addInterceptor(OAuth1Interceptor(goodreadsService))

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(StethoInterceptor())
        }

        val client = builder.build();

        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.goodreads.com")
                .client(client)
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        goodreadsApi = retrofit.create(GoodreadsApi::class.java)
    }

//    fun getAuthorBooks() = goodreadsApi.getAuthorBooks()
}