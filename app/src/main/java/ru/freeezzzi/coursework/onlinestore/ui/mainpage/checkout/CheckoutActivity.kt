package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R

class CheckoutActivity : AppCompatActivity(R.layout.checkout_activity) {
    override fun attachBaseContext(newBase: Context) {
        App.appComponent.inject(this)
        super.attachBaseContext(newBase)
    }
}