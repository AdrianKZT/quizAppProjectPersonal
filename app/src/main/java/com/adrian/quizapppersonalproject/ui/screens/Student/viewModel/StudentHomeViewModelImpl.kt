package com.adrian.quizapppersonalproject.ui.screens.Student.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.adrian.quizapppersonalproject.core.service.AuthService
import com.adrian.quizapppersonalproject.data.model.User
import com.adrian.quizapppersonalproject.data.repo.QuizRepo
import com.adrian.quizapppersonalproject.data.repo.UserRepo
import com.adrian.quizapppersonalproject.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentHomeViewModelImpl @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo,
    private val quizRepo: QuizRepo
): BaseViewModel(), StudentHomeViewModel {

    private val _user = MutableStateFlow(User(email="a@b.com", name="Kiren", role = "Student"))
    val user: StateFlow<User> = _user

    private val _profileUri = MutableStateFlow<Uri?>(null)
    val profileUri: StateFlow<Uri?> = _profileUri

    init {
        getCurrentUser()
        //getProfilePicUri()
    }

    override fun getQuizById(quizId: String) {
        Log.d("debugging", quizId)

        viewModelScope.launch(Dispatchers.IO) {
            quizRepo.getQuizByID(quizId).let {
                Log.d("debugging", it.toString())
            }
        }
    }

    override fun logout() {
        viewModelScope.launch {
            errorHandler {
                authService.logout()
            }
        }
    }



//    fun updateProfilePic(uri: Uri){
//        user.value.id?.let {
//            viewModelScope.launch(Dispatchers.IO) {
//                val name = "${it}.jpg"
//                storageService.addImage(name, uri)
//            }
//        }
//    }

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