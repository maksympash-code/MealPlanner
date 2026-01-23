package com.example.mealplanner.di

import com.example.mealplanner.BuildConfig
import com.example.mealplanner.data.remote.RetrofitService
import com.example.mealplanner.data.remote.RetrofitServiceApiFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    fun provideRetrofitService(): RetrofitService {
        return RetrofitServiceApiFactory.create()
    }
}