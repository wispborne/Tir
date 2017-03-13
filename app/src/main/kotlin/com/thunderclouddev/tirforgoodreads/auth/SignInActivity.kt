package com.thunderclouddev.tirforgoodreads.auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.scribejava.core.builder.ServiceBuilder
import com.thunderclouddev.tirforgoodreads.logging.timberkt.TimberKt
import org.fuckboilerplate.rx_social_connect.RxSocialConnect

/**
 * @author David Whitman on 13 Mar, 2017.
 */
class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trySignIn()
    }


    private fun trySignIn() {
        val goodReadsService = ServiceBuilder()
                .apiKey("KUtQGqdhmKy1nUyQnFZRzA")
                .apiSecret("qlhTxMwfzA7eOeCVKNiG9BeCjwuaf6xo7F2Jjqsfzeo")
                .callback("oauth://goodreads")
                .build(GoodreadsApi())

        RxSocialConnect.with(this@SignInActivity, goodReadsService)
                .subscribe { response ->
                    Toast.makeText(this@SignInActivity, response.token().token, Toast.LENGTH_LONG).show()
                    TimberKt.d { response.token().token }
                }
    }
}