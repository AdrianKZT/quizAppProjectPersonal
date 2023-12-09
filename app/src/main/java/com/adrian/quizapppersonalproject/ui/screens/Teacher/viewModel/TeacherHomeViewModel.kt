package com.adrian.quizapppersonalproject.ui.screens.Teacher.viewModel

import com.adrian.quizapppersonalproject.data.model.Quiz
import kotlinx.coroutines.flow.StateFlow

interface TeacherHomeViewModel {
    val quizzes: StateFlow<List<Quiz>>

    fun logout()
}