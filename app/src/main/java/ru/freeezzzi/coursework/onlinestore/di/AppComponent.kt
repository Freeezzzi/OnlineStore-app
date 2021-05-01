package ru.freeezzzi.coursework.onlinestore.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.BindsInstance
import dagger.Component
import ru.freeezzzi.coursework.onlinestore.App
import ru.freeezzzi.coursework.onlinestore.di.modules.*
import ru.freeezzzi.coursework.onlinestore.di.modules.AppModule
import ru.freeezzzi.coursework.onlinestore.di.modules.DataModule
import ru.freeezzzi.coursework.onlinestore.di.modules.NetworkModule
import ru.freeezzzi.coursework.onlinestore.domain.repositories.AuthRepository
import ru.freeezzzi.coursework.onlinestore.domain.repositories.CategoriesRepository
import ru.freeezzzi.coursework.onlinestore.domain.repositories.ProductsRepository
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.MainActivity
import ru.freeezzzi.coursework.onlinestore.ui.SplashActivity
import ru.freeezzzi.coursework.onlinestore.ui.loginregistration.LoginActivity
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.checkout.CheckoutActivity
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, NetworkModule::class, ViewModelFactoryModule::class])
interface AppComponent {
    // fun provideRouter(): Router
    fun provideViewModelFactory(): ViewModelProvider.Factory

    fun provideCategoriesRepository(): CategoriesRepository

    fun provideProductsRepository(): ProductsRepository

    fun provideAuthRepository(): AuthRepository

    fun inject(app: App)

    fun inject(appActivity: SplashActivity)

    fun inject(appActivity: MainActivity)

    fun inject(appActivity: LoginActivity)

    fun inject(appActivity: CheckoutActivity)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun apiUrl(@Named(NetworkModule.BASE_URL) url: String): Builder

        @BindsInstance
        fun appContext(context: Context): Builder
    }
}
