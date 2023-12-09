package com.adrian.quizapppersonalproject.ui.screens.ShowQuizQuestionPage


import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.adrian.quizapppersonalproject.R
import com.adrian.quizapppersonalproject.data.model.Question
import com.adrian.quizapppersonalproject.databinding.FragmentQuizPageBinding
import com.adrian.quizapppersonalproject.ui.screens.ShowQuizQuestionPage.viewModel.ShowQuizPageViewModelImpl
import com.adrian.quizapppersonalproject.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowQuizPageFragment : BaseFragment<FragmentQuizPageBinding>() {
    override val viewModel: ShowQuizPageViewModelImpl by viewModels()

    private val args: ShowQuizPageFragmentArgs by navArgs()

    private var answer = ""

    private var countCorrect = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getQuizById(args.quizId)
    }

    override fun setupUIComponents(view: View) {
        super.setupUIComponents(view)

        binding.run {

            btnCheck.setOnClickListener {
                val selectedAnswer = when {
                    RB1.isChecked -> RB1
                    RB2.isChecked -> RB2
                    RB3.isChecked -> RB3
                    else -> RB4 }

                val selectedRadioButton = selectedAnswer.text
                val isCorrect = selectedRadioButton == answer

                val bgColor = if (isCorrect) {
                    countCorrect++
                    R.color.green
                } else {
                    R.color.red
                }

                // Set background resource for the selected RadioButton
                selectedAnswer.setBackgroundResource(bgColor)

                // Delay clearing the background color if you want to show it for a short duration
                Handler(Looper.getMainLooper()).postDelayed({

                    // Clear the background color
                    nextQuestion()

                    // Move to the next question
                    viewModel.nextQuestion()

                }, 1000) // Adjust the delay duration as needed
            }
        }

    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.idx.collect {
                if (it >= 0) {
                    if (it != viewModel.questions.value.size) {
                        setQuestionText(viewModel.questions.value[it])
                    } else if (it == viewModel.questions.value.size) {
                        viewModel.addResult(countCorrect.toString())
                    }
                }
                if (it == viewModel.questions.value.size) {
                    val bundle = Bundle()
                    bundle.putBoolean("showLeaderBoard", true)
                    setFragmentResult("finish_quiz", bundle)
                    navController.popBackStack()

                }
            }
        }

        lifecycleScope.launch {
            viewModel.quiz.collect {
                if (it.time.toInt() != null || it.time.toInt() > 0) {

                    object : CountDownTimer(it.time * 1000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            val updateTimer = millisUntilFinished / 1000
                            binding.tvShowTimer.text = "Time remaining: $updateTimer seconds"
                        }

                        override fun onFinish() {
                        }

                    }.start()
                }
            }
        }
    }


    fun nextQuestion() {
        binding.run {
            radioGroup.clearCheck()
            RB1.setBackgroundResource(android.R.color.transparent)
            RB2.setBackgroundResource(android.R.color.transparent)
            RB3.setBackgroundResource(android.R.color.transparent)
            RB4.setBackgroundResource(android.R.color.transparent)
        }
    }


    fun setQuestionText(currentQuestion: Question) {
        binding.run {
            tvQuestion.text = currentQuestion.title

            RB1.text = currentQuestion.op1
            RB2.text = currentQuestion.op2
            RB3.text = currentQuestion.op3
            RB4.text = currentQuestion.op4

            answer = currentQuestion.ans
        }
    }
}