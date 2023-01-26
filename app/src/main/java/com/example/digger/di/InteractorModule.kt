package com.example.digger.di

import com.example.digger.interactor.AuthInteractor
import com.example.digger.retrofits.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {


    @Singleton
    @Provides
    fun provideAuthInteractor(
        apiService: ApiService
    ): AuthInteractor {
        return AuthInteractor(apiService)
    }
}