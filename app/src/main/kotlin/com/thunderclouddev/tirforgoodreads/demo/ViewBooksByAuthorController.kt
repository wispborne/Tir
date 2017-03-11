package com.thunderclouddev.tirforgoodreads.demo

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.thunderclouddev.tirforgoodreads.BaseApp
import com.thunderclouddev.tirforgoodreads.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by David Whitman on 11 Mar, 2017.
 */
class ViewBooksByAuthorController : Controller() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        BaseApp.goodreadsApi.getAuthorBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                AlertDialog.Builder(activity)
                    .setMessage("Books by ${response.author.name}\n\n${response.author.books.items.joinToString(separator = "\n")}")
                    .show()
            }

        return inflater.inflate(R.layout.view_books_by_author, container)
    }

}