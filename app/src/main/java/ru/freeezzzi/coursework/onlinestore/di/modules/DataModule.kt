package ru.freeezzzi.coursework.onlinestore.di.modules

import dagger.Binds
import dagger.Module
import ru.freeezzzi.coursework.onlinestore.data.repositories.AuthRepositoryImpl
import ru.freeezzzi.coursework.onlinestore.data.repositories.CategoriesRepositoryImpl
import ru.freeezzzi.coursework.onlinestore.data.repositories.ProductsRepositoryImpl
import ru.freeezzzi.coursework.onlinestore.domain.repositories.AuthRepository
import ru.freeezzzi.coursework.onlinestore.domain.repositories.CategoriesRepository
import ru.freeezzzi.coursework.onlinestore.domain.repositories.ProductsRepository
import javax.inject.Singleton

@Module
internal abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun provideCategoriesRepository(categoriesRepositoryImpl: CategoriesRepositoryImpl): CategoriesRepository

    @Binds
    @Singleton
    abstract fun provideProductsRepository(productsRepositoryImpl: ProductsRepositoryImpl): ProductsRepository
}