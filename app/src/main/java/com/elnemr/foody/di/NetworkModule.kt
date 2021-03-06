package com.elnemr.foody.di

import android.content.Context
import com.elnemr.foody.data.network.ApiInterface
import com.elnemr.foody.util.Constants.Companion.BASE_URL
import com.elnemr.foody.util.NetworkListener
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()


    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor():HttpLoggingInterceptor = HttpLoggingInterceptor()

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

    @Singleton
    @Provides
    fun provideGsonConvertorFactory(gson : Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun provideApi(
        okHttpClient: OkHttpClient.Builder,
        converterFactory: GsonConverterFactory,
        loggingInterceptor: HttpLoggingInterceptor,
        builder: Retrofit.Builder
    ): ApiInterface {

        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addNetworkInterceptor(loggingInterceptor)
        okHttpClient.retryOnConnectionFailure(true)

        val client = okHttpClient
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

        return builder.client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
            .create(ApiInterface::class.java)
    }
}