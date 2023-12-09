package com.adrian.quizapppersonalproject.ui.screens.login.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.adrian.quizapppersonalproject.core.service.AuthService
import com.adrian.quizapppersonalproject.data.model.User
import com.adrian.quizapppersonalproject.data.repo.UserRepo
import com.adrian.quizapppersonalproject.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModelImpl @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo
) : BaseViewModel(), LoginViewModel {

    private val _user = MutableStateFlow(User(name = "Name", email = "Email", role = "Role"))
    val user: StateFlow<User> = _user


    override fun login(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = errorHandler {
                authService.login(email, pass)
            }

            if (result != null) {
                _success.emit("Login Successfully")

            } else {
                _error.emit("Login Failed")
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