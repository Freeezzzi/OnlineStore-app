package ru.freeezzzi.coursework.onlinestore.di.viewmodels

import dagger.Component
import ru.freeezzzi.coursework.onlinestore.di.AppComponent
import ru.freeezzzi.coursework.onlinestore.di.scopes.AppScope
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.category.CategoriesViewModel

@AppScope
@Component(dependencies = [AppComponent::class])
interface CategoriesViewModelComponent {
    fun provideViewModel() : CategoriesViewModel
}