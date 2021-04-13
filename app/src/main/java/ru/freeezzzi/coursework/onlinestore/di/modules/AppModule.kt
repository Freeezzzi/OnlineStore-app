package ru.freeezzzi.coursework.onlinestore.di.modules

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class AppModule {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideRouter(): Router =
        cicerone.router

    @Provides
    @Singleton
    fun provideNavigatorHolder(): NavigatorHolder =
        cicerone.getNavigatorHolder()
}