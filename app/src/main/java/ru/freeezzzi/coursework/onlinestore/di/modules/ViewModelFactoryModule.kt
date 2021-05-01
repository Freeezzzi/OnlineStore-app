package ru.freeezzzi.coursework.onlinestore.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.freeezzzi.coursework.onlinestore.di.scopes.AppScope
import ru.freeezzzi.coursework.onlinestore.di.scopes.ViewModelKey
import ru.freeezzzi.coursework.onlinestore.di.viewmodels.ViewModelFactory
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.cart.CartViewModel

@Module()
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    abstract fun bindCartViewModel(cartViewModel: CartViewModel): ViewModel

}