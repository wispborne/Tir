package com.thunderclouddev.tirforgoodreads.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

/**
 * @author David Whitman on 11 Mar, 2017.
 */
class GoodreadsApiBuilder {
    val goodreadsApi: GoodreadsApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.goodreads.com")
            .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        goodreadsApi = retrofit.create(GoodreadsApi::class.java)
    }

//    fun getAuthorBooks() = goodreadsApi.getAuthorBooks()
}