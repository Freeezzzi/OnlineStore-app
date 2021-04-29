package ru.freeezzzi.coursework.onlinestore.di.viewmodels

import dagger.Component
import ru.freeezzzi.coursework.onlinestore.di.AppComponent
import ru.freeezzzi.coursework.onlinestore.di.scopes.AppScope
import ru.freeezzzi.coursework.onlinestore.ui.loginregistration.LoginViewModel

@AppScope
@Component(dependencies = [AppComponent::class])
interface LoginViewModelComponent {
    fun provideViewModel(): LoginViewModel
}