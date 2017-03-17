package com.thunderclouddev.goodreadsapisdk.api

import com.github.scribejava.core.builder.api.DefaultApi10a
import com.github.scribejava.core.model.OAuth1RequestToken

/**
 * Created by David Whitman on 13 Mar, 2017.
 */
class GoodreadsOAuthApi : DefaultApi10a() {
    companion object {
        val CALLBACK_URI = "oauth://tir"
    }

    private val BASE_URL = "https://www.goodreads.com/oauth"

    override fun getRequestTokenEndpoint() = "$BASE_URL/request_token"

    override fun getAuthorizationUrl(requestToken: OAuth1RequestToken) = "$BASE_URL/authorize?oauth_token=${requestToken.token}&mobile=1&oauth_callback=${CALLBACK_URI}"

    override fun getAccessTokenEndpoint() = "$BASE_URL/access_token"
}