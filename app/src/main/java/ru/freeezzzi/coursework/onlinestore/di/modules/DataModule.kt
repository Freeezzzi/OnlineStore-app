package ru.freeezzzi.coursework.onlinestore.di.modules

import dagger.Binds
import dagger.Module
import ru.freeezzzi.coursework.onlinestore.data.repositories.*
import ru.freeezzzi.coursework.onlinestore.domain.repositories.*
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

    @Binds
    @Singleton
    abstract fun provideOrdersRepository(ordersRepositoryImpl: OrdersRepositoryImpl): OrdersRepository

    @Binds
    @Singleton
    abstract fun provideRecipesRepository(recipesRepositoryImpl: RecipesRepositoryImpl): RecipesRepository
}
