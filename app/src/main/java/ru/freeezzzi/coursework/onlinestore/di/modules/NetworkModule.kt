package ru.freeezzzi.coursework.onlinestore.di.modules

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.freeezzzi.coursework.onlinestore.data.network.ServerAPI
import ru.freeezzzi.coursework.onlinestore.data.network.TokenAuthenticator
import javax.inject.Named
import javax.inject.Singleton

@Module
internal class NetworkModule {
    @Provides
    @Singleton
    fun provideAuthenticator(tokenAuthenticator: TokenAuthenticator): Authenticator =
        tokenAuthenticator

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authenticator: Authenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient().newBuilder()
            .authenticator(authenticator)
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
//            .addInterceptor(AuthInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named(BASE_URL) baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ServerAPI = retrofit.create(ServerAPI::class.java)

    @Provides
    @Singleton
    internal fun provideMoshi(): Moshi = Moshi.Builder()
        .build()

    companion object {
        const val BASE_URL = "baseUrl"
    }
}