package com.elnemr.foody.di

import com.elnemr.foody.data.network.ApiInterface
import com.elnemr.foody.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

    @Singleton
    @Provides
    fun provideGsonConvertorFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideApi(
        okHttpClient: OkHttpClient.Builder,
        converterFactory: GsonConverterFactory,
        builder: Retrofit.Builder
    ): ApiInterface {

//        okHttpClient.addNetworkInterceptor()

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