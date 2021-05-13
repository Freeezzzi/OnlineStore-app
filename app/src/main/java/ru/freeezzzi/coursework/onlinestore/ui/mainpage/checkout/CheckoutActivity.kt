package ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R
import javax.inject.Inject

class CheckoutActivity : AppCompatActivity(R.layout.checkout_activity) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CheckoutViewModel by viewModels { viewModelFactory }

    private val checkoutActivityArgs: CheckoutActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.productsList = checkoutActivityArgs.Products.toMutableList()
    }

    override fun attachBaseContext(newBase: Context) {
        App.appComponent.inject(this)
        super.attachBaseContext(newBase)
    }
}
