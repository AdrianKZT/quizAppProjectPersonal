package com.adrian.quizapppersonalproject.ui.screens.QuizHomePage.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrian.quizapppersonalproject.core.service.AuthService
import com.adrian.quizapppersonalproject.data.model.Quiz
import com.adrian.quizapppersonalproject.data.repo.QuizRepo
import com.adrian.quizapppersonalproject.data.repo.UserRepo
import com.adrian.quizapppersonalproject.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizHomePageViewModelImpl @Inject constructor(
    private val quizRepo: QuizRepo,
    private val authService: AuthService,
    private val userRepo: UserRepo
) : BaseViewModel(), QuizHomePageViewModel {



    private val _quiz = MutableStateFlow(
        Quiz(
            title = "title",
            quizId = "quizID",
            time = 0,
            answers = emptyList(),
            createdBy = "",
            options = emptyList(),
            QuizTitles = emptyList())
    )
    val quiz: StateFlow<Quiz> = _quiz

    override fun getQuizByID(quizId: String) {
        viewModelScope.launch(Dispatchers.IO) {

            quizRepo.getQuizByID(quizId)?.let {
                //Log.d("debugging", "$quizId, $it")
                _quiz.emit(it)

            }
        }
    }


}