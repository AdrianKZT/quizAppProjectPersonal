package com.adrian.quizapppersonalproject.core.di

import com.adrian.quizapppersonalproject.core.service.AuthService
import com.adrian.quizapppersonalproject.core.service.AuthServiceImpl
import com.adrian.quizapppersonalproject.core.service.StorageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAuth(): AuthService {
        return AuthServiceImpl()
    }

    @Provides
    @Singleton
    fun provideStorageService(): StorageService {
        return StorageService()
    }
}