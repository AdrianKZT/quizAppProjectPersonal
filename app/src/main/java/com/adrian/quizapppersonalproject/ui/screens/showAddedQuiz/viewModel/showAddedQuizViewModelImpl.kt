package com.adrian.quizapppersonalproject.ui.screens.showAddedQuiz.viewModel


import com.adrian.quizapppersonalproject.data.model.Quiz
import com.adrian.quizapppersonalproject.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class showAddedQuizViewModelImpl @Inject constructor(): BaseViewModel(), showAddedQuiz {

    private val _quiz = MutableStateFlow(
        (1..2).map {
            Quiz(title = "title $it", quizId = "quiz ID $it", time = "time".toLong(), QuizTitles = emptyList(), options = emptyList(), answers = emptyList(), createdBy = "")
        }.toList()
    )

    override val quizzes: StateFlow<List<Quiz>> = _quiz

}