package com.adrian.quizapppersonalproject.ui.screens.AddQuiz.viewModel

import androidx.lifecycle.viewModelScope
import com.adrian.quizapppersonalproject.core.service.AuthService
import com.adrian.quizapppersonalproject.data.model.Question
import com.adrian.quizapppersonalproject.data.model.Quiz
import com.adrian.quizapppersonalproject.data.repo.QuizRepo
import com.adrian.quizapppersonalproject.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddQuizViewModelImpl @Inject constructor(
    private val quizRepo: QuizRepo,
    private val authService: AuthService
) : BaseViewModel(), AddQuizViewModel {


    private val _finish = MutableSharedFlow<Unit>()
    override val finish: SharedFlow<Unit> = _finish

    private val _quizQuestion = MutableStateFlow<List<Question>>(emptyList())
    val quizQuestion: StateFlow<List<Question>> = _quizQuestion

    override fun addQuiz(title: String, quizId: String, time: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val titles = mutableListOf<String>()
            val options = mutableListOf<String>()
            val answers = mutableListOf<String>()

            _quizQuestion.value.map {
                titles.add(it.title)
                options.add(it.op1)
                options.add(it.op2)
                options.add(it.op3)
                options.add(it.op4)
                answers.add(it.ans)
            }

            errorHandler {
                quizRepo.addQuiz(
                    Quiz(
                        title = title,
                        quizId = quizId,
                        time = time,
                        QuizTitles = titles,
                        options = options,
                        answers = answers,
                        createdBy = authService.getCurrentUser()?.uid.orEmpty()
                    )
                )
                _success.emit("Quiz added successfully")
            }
            _finish.emit(Unit)
        }
    }

    override fun readCsv(lines: List<String>) {
        viewModelScope.launch {
            try {
                lines.map { line ->
                    val title = line.split(",")
                    Question(
                        title = title[0],
                        op1 = title[1],
                        op2 = title[2],
                        op3 = title[3],
                        op4 = title[4],
                        ans = title[5]
                    )
                }.toList().let {
                    if (it.all { true }) {
                        _quizQuestion.emit(it)
                        _success.emit("CSV Import Successful")
                    } else {
                    }
                }
            } catch (e: Exception) {
            }
        }
    }
}
