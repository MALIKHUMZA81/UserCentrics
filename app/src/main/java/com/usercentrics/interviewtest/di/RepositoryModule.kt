package com.usercentrics.interviewtest.di

import com.usercentrics.interviewtest.data.repository.centrics.CentricsRepository
import com.usercentrics.interviewtest.data.repository.centrics.CentricsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * The Dagger Module for providing repository instances.
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideCentricsRepository(): CentricsRepository {
        return CentricsRepositoryImpl(Dispatchers.IO)
    }
}
