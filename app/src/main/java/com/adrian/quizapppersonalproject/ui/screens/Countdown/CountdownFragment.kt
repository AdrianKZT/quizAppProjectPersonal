package com.adrian.quizapppersonalproject.ui.screens.Countdown

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.adrian.quizapppersonalproject.R
import com.adrian.quizapppersonalproject.databinding.FragmentCountdownBinding
import com.adrian.quizapppersonalproject.ui.screens.Countdown.viewModel.CountdownViewModel
import com.adrian.quizapppersonalproject.ui.screens.base.BaseFragment
import com.adrian.quizapppersonalproject.ui.screens.base.viewModel.BaseViewModel

class CountdownFragment : BaseFragment<FragmentCountdownBinding>() {
    override val viewModel : CountdownViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountdownBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents(view: View) {
        super.setupUIComponents(view)

        binding.tvCountdown
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
    }

}