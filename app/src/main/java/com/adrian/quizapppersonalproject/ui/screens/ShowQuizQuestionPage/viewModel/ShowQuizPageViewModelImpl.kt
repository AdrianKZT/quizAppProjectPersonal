package com.adrian.quizapppersonalproject.ui.screens.ShowQuizQuestionPage.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.adrian.quizapppersonalproject.core.service.AuthService
import com.adrian.quizapppersonalproject.data.model.Question
import com.adrian.quizapppersonalproject.data.model.Quiz
import com.adrian.quizapppersonalproject.data.model.Result
import com.adrian.quizapppersonalproject.data.model.User
import com.adrian.quizapppersonalproject.data.repo.QuizRepo
import com.adrian.quizapppersonalproject.data.repo.ResultRepo
import com.adrian.quizapppersonalproject.data.repo.UserRepo
import com.adrian.quizapppersonalproject.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class ShowQuizPageViewModelImpl @Inject constructor(
    private val quizRepo: QuizRepo,
    private val authService: AuthService,
    private val userRepo: UserRepo,
    private val resultRepo: ResultRepo
) : BaseViewModel(), ShowQuizPageViewModel {

    private val _user = MutableStateFlow(User(name = "Name", email = "Email", role = "Role"))
    val user: StateFlow<User> = _user

    private val _idx = MutableStateFlow(-1)
    val idx: StateFlow<Int> = _idx

    private val _questions: MutableStateFlow<List<Question>> = MutableStateFlow(emptyList())
    val questions: StateFlow<List<Question>> = _questions

    private val _quiz: MutableStateFlow<Quiz> = MutableStateFlow(
        Quiz(
            QuizTitles = emptyList(),
            options = emptyList(),
            answers = emptyList(),
            createdBy = "",
            quizId = "",
            time = 0,
            title = ""
        )
    )

    val quiz: StateFlow<Quiz> = _quiz

    init {
        getCurrentUser()
    }

    override fun getQuizById(quizId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            quizRepo.getQuizByID(quizId)?.let {
                Log.d("debugging", "hello $it")
                _quiz.value = it
                val ques = mutableListOf<Question>()
                it.QuizTitles.forEachIndexed { index, s ->
                    val q = Question(
                        title = s,
                        op1 = it.options[index * 4],
                        op2 = it.options[index * 4 + 1],
                        op3 = it.options[index * 4 + 2],
                        op4 = it.options[index * 4 + 3],
                        ans = it.answers[index]
                    )
                    ques.add(q)
                }
                _questions.emit(ques)
                _idx.emit(0)
                Log.d("debugging", "getQuizById: ${_questions.value}")
            }
        }
    }

    fun nextQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            _idx.emit(_idx.value+1)
        }
    }

    fun addResult(result: String) {
        viewModelScope.launch(Dispatchers.IO) {
            resultRepo.addResult(
                Result(
                    name = user.value.name,
                    correctScore = result,
                    quizID = _quiz.value.quizId
                )
            )
        }
    }


    fun getCurrentUser() {
        val firebaseUser = authService.getCurrentUser()
        firebaseUser?.let {
            viewModelScope.launch(Dispatchers.IO) {
                errorHandler {
                    userRepo.getUser(it.uid)
                }?.let { user ->
                    _user.value = user
                }
            }
        }
    }
}