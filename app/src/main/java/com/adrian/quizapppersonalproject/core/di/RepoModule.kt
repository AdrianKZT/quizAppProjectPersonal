package com.adrian.quizapppersonalproject.core.di

import com.adrian.quizapppersonalproject.core.service.AuthService
import com.adrian.quizapppersonalproject.data.repo.QuizRepo
import com.adrian.quizapppersonalproject.data.repo.QuizRepoImpl
import com.adrian.quizapppersonalproject.data.repo.ResultRepo
import com.adrian.quizapppersonalproject.data.repo.ResultRepoImpl
import com.adrian.quizapppersonalproject.data.repo.UserRepo
import com.adrian.quizapppersonalproject.data.repo.UserRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    @Singleton
    fun provideUserRepo(authService: AuthService): UserRepo {
        return UserRepoImpl(authService = authService)
    }

    @Provides
    @Singleton
    fun provideQuizRepo(authService: AuthService): QuizRepo{
        return QuizRepoImpl(authService = authService)
    }

    @Provides
    @Singleton
    fun provideResultRepo(authService: AuthService): ResultRepo{
        return ResultRepoImpl(authService = authService)
    }
}