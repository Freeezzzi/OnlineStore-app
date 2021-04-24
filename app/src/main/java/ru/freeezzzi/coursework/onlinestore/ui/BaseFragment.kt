package ru.freeezzzi.coursework.onlinestore.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment(private val layoutResource: Int) : Fragment(layoutResource) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    open fun initViews(view: View) {}

    open fun showError(msg: String, view: View) {
        var errorMsg = msg
/*        when (errorMsg) {
            "HTTP 429 " -> errorMsg = getString(R.string.limit_error)
            "Tickers loading.Try again!" -> return // В этом случае просто ждем
            "HTTP 403 " -> errorMsg = getString(R.string.access_denied)
            "Unable to resolve host \"finnhub.io\": No address associated with hostname" ->
                errorMsg = getString(R.string.internet_issues)
        }*/
        val snackbar = Snackbar.make(view, "$errorMsg. Swipe to refresh", Snackbar.LENGTH_LONG)
        snackbar.view.setOnClickListener { snackbar.dismiss() }
        snackbar.show()
    }
}
