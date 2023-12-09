package com.adrian.quizapppersonalproject.ui.screens.QuizHomePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.adrian.quizapppersonalproject.databinding.FragmentQuizHomePageBinding
import com.adrian.quizapppersonalproject.ui.screens.QuizHomePage.viewModel.QuizHomePageViewModelImpl
import com.adrian.quizapppersonalproject.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizHomePageFragment : BaseFragment<FragmentQuizHomePageBinding>() {
    override val viewModel: QuizHomePageViewModelImpl by viewModels()
    private val args: QuizHomePageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getQuizByID(args.quizId)
    }

    override fun setupUIComponents(view: View) {
        super.setupUIComponents(view)

        binding.run {

            btnStart.setOnClickListener {
                val action = QuizHomePageFragmentDirections.actionQuizHomePageToQuizPage(args.quizId)
                navController.navigate(action)

            }

            btnBack.setOnClickListener {
                navController.popBackStack()
            }

        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.quiz.collect{
                binding.run {
                    tvQuizID.text = it.quizId
                    tvQuizTitle.text = it.title
                    tvQuizTime.text = it.time.toString()
//                    tvCreated.text = it.createdBy
                }
            }
        }
    }
}