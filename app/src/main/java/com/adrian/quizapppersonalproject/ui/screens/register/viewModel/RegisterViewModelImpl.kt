package com.adrian.quizapppersonalproject.ui.screens.register.viewModel

import androidx.lifecycle.viewModelScope
import com.adrian.quizapppersonalproject.core.service.AuthService
import com.adrian.quizapppersonalproject.data.model.User
import com.adrian.quizapppersonalproject.data.repo.UserRepo
import com.adrian.quizapppersonalproject.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModelImpl @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo
) : BaseViewModel(), RegisterViewModel {

    private val _user = MutableStateFlow(User(name = "Name", email = "Email", role = "Role"))
    val user: StateFlow<User> = _user

    override fun register(
        name: String,
        email: String,
        pass: String,
        confirmPass: String,
        role: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = errorHandler {
                authService.register(email, pass, role)
            }
            if (user != null) {
                _success.emit("Registered Successfully")
                errorHandler {
                    userRepo.addUser(
                        User(
                            name = name,
                            email = email,
                            role = role
                        )
                    )
                }
            }
        }

    }
}