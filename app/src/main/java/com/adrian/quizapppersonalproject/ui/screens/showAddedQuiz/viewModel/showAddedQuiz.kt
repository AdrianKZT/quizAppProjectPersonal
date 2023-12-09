package com.adrian.quizapppersonalproject.ui.screens.showAddedQuiz.viewModel

import com.adrian.quizapppersonalproject.data.model.Quiz
import kotlinx.coroutines.flow.StateFlow

interface showAddedQuiz {

    val quizzes: StateFlow<List<Quiz>>
}