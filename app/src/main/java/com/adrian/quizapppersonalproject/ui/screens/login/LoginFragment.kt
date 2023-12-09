package com.adrian.quizapppersonalproject.ui.screens.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.adrian.quizapppersonalproject.R
import com.adrian.quizapppersonalproject.databinding.FragmentLoginBinding
import com.adrian.quizapppersonalproject.ui.screens.base.BaseFragment
import com.adrian.quizapppersonalproject.ui.screens.login.viewModel.LoginViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val viewModel: LoginViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents(view: View) {
        super.setupUIComponents(view)

        binding.run {
            btnLogin.setOnClickListener {
                viewModel.login(
                    etEmail.text.toString(),
                    etPass.text.toString()
                )
            }

            tvSignUpNow.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginToRegister()
                navController.navigate(action)
                Log.d("debugging", "Here")
            }
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.user.collect { user ->
                val action = when (user.role) {
                    "Student" -> R.id.toStudentHome
                    "Teacher" -> R.id.toTeacherHome
                    else -> null
                }
                action?.let { navController.navigate(action) }
            }
        }

        lifecycleScope.launch {
            viewModel.success.collect{
                viewModel.getCurrentUser()
            }
        }
    }
}