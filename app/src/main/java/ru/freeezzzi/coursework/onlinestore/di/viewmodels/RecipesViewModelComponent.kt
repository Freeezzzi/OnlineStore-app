package ru.freeezzzi.coursework.onlinestore.di.viewmodels

import dagger.Component
import ru.freeezzzi.coursework.onlinestore.di.AppComponent
import ru.freeezzzi.coursework.onlinestore.di.scopes.AppScope
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.recipes.RecipesViewModel

@AppScope
@Component(dependencies = [AppComponent::class])
interface RecipesViewModelComponent {
    fun provideViewModel(): RecipesViewModel
}