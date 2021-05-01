package ru.freeezzzi.coursework.onlinestore.di.viewmodels

import dagger.Component
import ru.freeezzzi.coursework.onlinestore.di.AppComponent
import ru.freeezzzi.coursework.onlinestore.di.scopes.AppScope
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.cart.CartViewModel

@AppScope
@Component(dependencies = [AppComponent::class])
interface CartViewModelComponent {
    fun provideViewModel(): CartViewModel
}