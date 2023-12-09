package com.adrian.quizapppersonalproject.ui.screens.Student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.adrian.quizapppersonalproject.databinding.FragmentStudentHomeBinding
import com.adrian.quizapppersonalproject.ui.screens.Student.viewModel.StudentHomeViewModelImpl
import com.adrian.quizapppersonalproject.ui.screens.base.BaseFragment
import com.adrian.quizapppersonalproject.ui.screens.tabcontainer.TabContainerFragmentDirections
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StudentHomeFragment : BaseFragment<FragmentStudentHomeBinding>() {
    override val viewModel: StudentHomeViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents(view: View) {
        super.setupUIComponents(view)

        binding.run {
            btnSearch.setOnClickListener {
                val prefixText = "#"
                val quizId = etQuizID.text.toString()

                if (quizId.isEmpty()) {
                    Snackbar.make(view, "Please enter your Quiz ID", Snackbar.LENGTH_LONG).show()
                } else if (!quizId.startsWith(prefixText)) {
                    Snackbar.make(view, "Quiz ID must start with '$prefixText'", Snackbar.LENGTH_LONG).show()
                } else {
                    viewModel.getQuizById(quizId)
                    val action =
                        TabContainerFragmentDirections.actionTabContainerToQuizHomePage(etQuizID.text.toString())
                    navController.navigate(action)
                }

            }

            ivLogout.setOnClickListener {
                viewModel.logout()
                val action = StudentHomeFragmentDirections.toLogin()
                navController.navigate(action)
            }
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.user.collect{
                binding.run {
                    tvEmail.text = it.email
                    tvName.text = it.name
                    tvRole.text = it.role
                }
            }
        }
    }
}