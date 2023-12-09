package com.adrian.quizapppersonalproject.data.repo

import com.adrian.quizapppersonalproject.data.model.Quiz
import kotlinx.coroutines.flow.Flow


interface QuizRepo {
    suspend fun getQuiz(): Flow<List<Quiz>>

    suspend fun addQuiz(quiz: Quiz)

    suspend fun getQuizByID(quizID: String): Quiz?

    suspend fun deleteQuiz(id: String)

    suspend fun updateQuiz(id: String, quiz: Quiz)
}