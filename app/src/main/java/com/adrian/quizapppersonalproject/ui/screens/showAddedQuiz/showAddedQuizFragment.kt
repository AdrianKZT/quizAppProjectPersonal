package com.adrian.quizapppersonalproject.ui.screens.showAddedQuiz


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrian.quizapppersonalproject.databinding.FragmentShowAddedQuizBinding
import com.adrian.quizapppersonalproject.ui.adapter.showQuizAddedAdapter
import com.adrian.quizapppersonalproject.ui.screens.base.BaseFragment
import com.adrian.quizapppersonalproject.ui.screens.showAddedQuiz.viewModel.showAddedQuizViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class showAddedQuizFragment : BaseFragment<FragmentShowAddedQuizBinding>() {

    override val viewModel: showAddedQuizViewModelImpl by viewModels()

    private lateinit var adapter: showQuizAddedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowAddedQuizBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun setupUIComponents(view: View) {
        super.setupUIComponents(view)

        setupAdapter()
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.quizzes.collect{
                adapter.showQuiz(it)
            }
        }
    }

    private fun setupAdapter() {
        adapter = showQuizAddedAdapter(emptyList())

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvShowAddedQuiz.adapter = adapter
        binding.rvShowAddedQuiz.layoutManager = layoutManager
    }
}