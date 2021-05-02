package ru.freeezzzi.coursework.onlinestore.di.viewmodels

import dagger.Component
import ru.freeezzzi.coursework.onlinestore.di.AppComponent
import ru.freeezzzi.coursework.onlinestore.di.scopes.AppScope
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout.CheckoutViewModel

@AppScope
@Component(dependencies = [AppComponent::class])
interface CheckoutViewModelComponent {
    fun provideViewModel(): CheckoutViewModel
}
