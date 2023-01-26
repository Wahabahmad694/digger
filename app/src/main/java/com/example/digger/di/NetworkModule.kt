package com.example.digger.di

import android.content.Context
import com.example.dig.BuildConfig
import com.example.digger.conectivity.ConnectivityProvider
import com.example.digger.retrofits.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideConnectivityModule(@ApplicationContext context: Context): ConnectivityProvider {
        return ConnectivityProvider.createProvider(context)
    }


    @Singleton
    @Provides
    fun provideGigService(okHttpClient: OkHttpClient): ApiService {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://vyod.manaknightdigital.com/member/api/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(okHttpClient)

        return retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideInterceptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
            .writeTimeout(5, TimeUnit.MINUTES) // write timeout
            .readTimeout(5, TimeUnit.MINUTES) // read timeout
            .addNetworkInterceptor { chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                    .method(original.method, original.body)
                /*     if (!SharedPrefsHelper.getHwUserAuth().isNullOrEmpty()) {
                         SharedPrefsHelper.getHwUserAuth()?.let {
                             requestBuilder
                                 .addHeader("x-user-email", SharedPrefsHelper.getHwUserEmail())
                                 .addHeader("x-user-token", it)
                         }
                     }*/

                requestBuilder.addHeader("cache-control", "no-cache")
                val request = requestBuilder.build()
                chain.proceed(request)
            }

        if (BuildConfig.DEBUG)
            okHttpClient.addInterceptor(logging)

        return okHttpClient.build()
    }
}