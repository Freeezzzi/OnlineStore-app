package ru.freeezzzi.coursework.onlinestore.ui

import android.content.Context
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun String.toPrice() = this + Html.fromHtml(" &#x20bd")
