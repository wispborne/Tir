package com.thunderclouddev.tirforgoodreads.api

import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.model.OAuthConstants
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
import com.github.scribejava.core.oauth.OAuth10aService
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.Response
import org.fuckboilerplate.rx_social_connect.NotActiveTokenFoundException
import org.fuckboilerplate.rx_social_connect.RxSocialConnect
import java.io.IOException

class OAuth1Interceptor(protected val service: OAuth10aService) : Interceptor {

    @Throws(IOException::class, NotActiveTokenFoundException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url().toString()
        val verb = apply(request.method())

        val requestOAuth = OAuthRequest(verb, url, service)
        val builderRequest = request.newBuilder()

        val token = oToken.blockingFirst()
        service.signRequest(token, requestOAuth)

        val headers = requestOAuth.headers

        for ((key, value) in headers) {
            builderRequest.addHeader(key, value)
        }

        // NOTE: ADDED BY DAVID WHITMAN because for some reason, OAuth10aService isn't adding it.
        builderRequest.addHeader(OAuthConstants.TOKEN_SECRET, token.tokenSecret)

        val response = chain.proceed(builderRequest.build())
        return response
    }

    private fun apply(method: String): Verb? {
        if (method.equals("GET", ignoreCase = true))
            return Verb.GET
        else if (method.equals("POST", ignoreCase = true))
            return Verb.POST
        else if (method.equals("PUT", ignoreCase = true))
            return Verb.PUT
        else if (method.equals("DELETE", ignoreCase = true))
            return Verb.DELETE
        else if (method.equals("HEAD", ignoreCase = true))
            return Verb.HEAD
        else if (method.equals("OPTIONS", ignoreCase = true))
            return Verb.OPTIONS
        else if (method.equals("TRACE", ignoreCase = true))
            return Verb.TRACE
        else if (method.equals("PATCH", ignoreCase = true)) return Verb.PATCH
        return null
    }

    //Exists for testing purposes
    protected val oToken: Observable<OAuth1AccessToken>
        get() = RxSocialConnect.getTokenOAuth1(service.api.javaClass)
}