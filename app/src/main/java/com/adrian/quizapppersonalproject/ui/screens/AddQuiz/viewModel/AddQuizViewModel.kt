package com.adrian.quizapppersonalproject.ui.screens.AddQuiz.viewModel

import kotlinx.coroutines.flow.SharedFlow

interface AddQuizViewModel {
    val finish: SharedFlow<Unit>

    fun addQuiz(title: String, quizId: String, time: Long)

    fun readCsv(lines:List<String>)

}