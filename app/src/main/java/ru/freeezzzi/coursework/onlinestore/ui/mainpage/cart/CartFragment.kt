package ru.freeezzzi.coursework.onlinestore.ui.mainpage.cart

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.di.viewmodels.DaggerCartViewModelComponent
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment

class CartFragment : BaseFragment(R.layout.cart_fragment) {

    private val viewModel: CartViewModel by viewModels(
        factoryProducer = { CartViewModelFactory() }
    )
}

class CartViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DaggerCartViewModelComponent.builder()
            .appComponent(App.appComponent)
            .build()
            .provideViewModel() as T
    }
}
