package com.adrian.quizapppersonalproject.ui.screens.Teacher.viewModel


import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.adrian.quizapppersonalproject.core.service.AuthService
import com.adrian.quizapppersonalproject.core.service.StorageService
import com.adrian.quizapppersonalproject.data.model.Quiz
import com.adrian.quizapppersonalproject.data.model.User
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
class TeacherHomeViewModelImpl @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo,
    private val quizRepo: QuizRepo,
    private val storageService: StorageService
) : BaseViewModel(), TeacherHomeViewModel {

    private val _user = MutableStateFlow(User(email = "email", name = "name", role = "role"))
    val user: StateFlow<User> = _user

    private val _quiz = MutableStateFlow<List<Quiz>>(emptyList())
    override val quizzes: StateFlow<List<Quiz>> = _quiz

    private val _profileUri = MutableStateFlow<Uri?>(null)
    val profileUri: StateFlow<Uri?> = _profileUri

    init {
        getCurrentUser()
        getQuiz()
        getProfilePicUri()
    }

    override fun logout() {
        viewModelScope.launch {
            errorHandler {
                authService.logout()
            }
        }
    }

    fun updateProfilePic(uri: Uri) {
        user.value.id?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val name = "${it}.jpg"
                storageService.addImage(name, uri)
                getProfilePicUri()
            }
        }
    }

    fun getProfilePicUri() {
        viewModelScope.launch(Dispatchers.IO) {
            authService.getCurrentUser()?.uid.let {

                _profileUri.value = storageService.getImage("${it}.jpg")
            }
        }
    }


    fun delete(quiz: Quiz) {
        viewModelScope.launch(Dispatchers.IO) {
            quizRepo.deleteQuiz(quiz.id ?: "")
        }
    }

    fun getQuiz() {
        viewModelScope.launch(Dispatchers.IO) {
            quizRepo.getQuiz().collect{
                _quiz.value = it
            }
        }
    }

    fun getCurrentUser() {
        val firebaseUser = authService.getCurrentUser()
        firebaseUser?.let {
            viewModelScope.launch(Dispatchers.IO) {
                errorHandler { userRepo.getUser(it.uid) }?.let { user ->
                    _user.value = user
                }
            }
        }
    }

}