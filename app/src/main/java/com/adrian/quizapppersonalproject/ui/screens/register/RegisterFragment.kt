package com.adrian.quizapppersonalproject.ui.screens.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.adrian.quizapppersonalproject.R
import com.adrian.quizapppersonalproject.databinding.FragmentRegisterBinding
import com.adrian.quizapppersonalproject.ui.screens.base.BaseFragment
import com.adrian.quizapppersonalproject.ui.screens.register.viewModel.RegisterViewModelImpl
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override val viewModel: RegisterViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents(view: View) {
        super.setupUIComponents(view)

        binding.run {
            btnSignUp.setOnClickListener {
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val confirmPassword = etConfirmPassword.text.toString()
                val selectedRole = autoCompleteTv.text.toString()

                if (name.isEmpty()|| email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || selectedRole.isEmpty()) {
                    Snackbar.make(view, "Please fill in all fields", Snackbar.LENGTH_LONG).show()
                } else if(password != confirmPassword) {
                    Snackbar.make(view, "Password and Confirm Password is not the same", Snackbar.LENGTH_LONG).show()
                } else {
                    viewModel.register(name, email, password, confirmPassword, selectedRole)
                }
            }

            tvSignIn.setOnClickListener {
                navController.popBackStack()
            }

            val role = resources.getStringArray(R.array.role)
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_items, role)
            autoCompleteTv.setAdapter(arrayAdapter)
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

//        lifecycleScope.launch {
//            viewModel.user.collect{
//                val action = RegisterFragmentDirections.toLogin()
//                navController.navigate(action)
//            }
//        }

        lifecycleScope.launch {
            viewModel.user.collect{ user ->
                val action = when (user.role) {
                    "Student" -> RegisterFragmentDirections.toStudentHome()
                    "Teacher" -> RegisterFragmentDirections.toTeacherHome()
                    else -> null
                }
                action?.let { navController.navigate(action) }
            }
        }

    }
}