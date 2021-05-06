package ru.freeezzzi.coursework.onlinestore.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.InternalCoroutinesApi
import ru.freeezzzi.coursework.onlinestore.data.local.LocalDatabase
import javax.inject.Singleton

@Module
internal class AppModule {

    @InternalCoroutinesApi
    @Provides
    @Singleton
    fun provideDatabase(appContext: Context): LocalDatabase =
        LocalDatabase.getInstance(appContext)
}