package ru.freeezzzi.coursework.onlinestore

import android.app.Application
import ru.freeezzzi.coursework.onlinestore.di.AppComponent
import ru.freeezzzi.coursework.onlinestore.di.DaggerAppComponent
import javax.inject.Inject

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appContext(this)
            .apiUrl(BuildConfig.BASE_URL)
            .build()
        appComponent.inject(this)
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}