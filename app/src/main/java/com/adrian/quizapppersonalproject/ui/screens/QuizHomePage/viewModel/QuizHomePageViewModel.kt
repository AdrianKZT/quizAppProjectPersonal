package com.adrian.quizapppersonalproject.ui.screens.QuizHomePage.viewModel

import com.adrian.quizapppersonalproject.data.model.Quiz
import kotlinx.coroutines.flow.StateFlow

interface QuizHomePageViewModel {

    fun getQuizByID(quizId: String)
}