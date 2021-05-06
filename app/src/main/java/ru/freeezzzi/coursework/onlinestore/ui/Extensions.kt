package ru.freeezzzi.coursework.onlinestore.ui

import android.content.Context
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import ru.freeezzzi.coursework.onlinestore.R

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun String.toPrice() = this + Html.fromHtml(" &#x20bd")

fun ImageView.setPicture(imageUrl: String, ) {
    if (imageUrl.isEmpty()) {
        this.setImageResource(R.color.white)
    } else {
        Picasso.get().isLoggingEnabled = true
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.color.white)
            .error(R.color.white)
            // .transform(transformation)
            .fit()
            .centerInside()
            // .centerCrop()
            .into(this)
    }
}
