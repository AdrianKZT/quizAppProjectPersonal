package com.adrian.quizapppersonalproject.ui.screens.Leaderboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrian.quizapppersonalproject.databinding.FragmentLeaderBoardBinding
import com.adrian.quizapppersonalproject.ui.adapter.leaderBoardAdapter
import com.adrian.quizapppersonalproject.ui.screens.Leaderboard.viewModel.LeaderBoardViewModelImpl
import com.adrian.quizapppersonalproject.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LeaderBoardFragment : BaseFragment<FragmentLeaderBoardBinding>() {
    override val viewModel : LeaderBoardViewModelImpl by viewModels()

    private lateinit var adapter: leaderBoardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaderBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents(view: View) {
        super.setupUIComponents(view)
        setupAdapter()
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.result.collect{
                adapter.showBoard(it)
            }
        }
    }

    private fun setupAdapter() {
        adapter = leaderBoardAdapter(emptyList())

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvLeaderBoard.adapter = adapter
        binding.rvLeaderBoard.layoutManager = layoutManager
    }
}