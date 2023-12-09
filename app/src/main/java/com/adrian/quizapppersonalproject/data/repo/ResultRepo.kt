package com.adrian.quizapppersonalproject.data.repo

import com.adrian.quizapppersonalproject.data.model.Result
import kotlinx.coroutines.flow.Flow

interface ResultRepo {

    suspend fun addResult(result: Result)

    suspend fun getResult(): Flow<List<Result>>
}