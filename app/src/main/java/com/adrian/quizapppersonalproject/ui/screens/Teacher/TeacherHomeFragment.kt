package com.adrian.quizapppersonalproject.ui.screens.Teacher

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrian.quizapppersonalproject.R
import com.adrian.quizapppersonalproject.data.model.Quiz
import com.adrian.quizapppersonalproject.databinding.FragmentTeacherHomeBinding
import com.adrian.quizapppersonalproject.ui.adapter.quizAddedAdapter
import com.adrian.quizapppersonalproject.ui.screens.Teacher.viewModel.TeacherHomeViewModelImpl
import com.adrian.quizapppersonalproject.ui.screens.base.BaseFragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TeacherHomeFragment : BaseFragment<FragmentTeacherHomeBinding>() {
    override val viewModel: TeacherHomeViewModelImpl by viewModels()

    private lateinit var adapter: quizAddedAdapter
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                viewModel.updateProfilePic(uri)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeacherHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents(view: View) {
        super.setupUIComponents(view)

        setupAdapter()

        binding.run {
            ivLogout.setOnClickListener {
                viewModel.logout()
                val action = TeacherHomeFragmentDirections.toLogin()
                navController.navigate(action)
            }

            ivAdd.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.user.collect {
                binding.run {
                    tvEmail.text = it.email
                    tvName.text = it.name
                    tvRole.text = it.role
                }
            }
        }

        lifecycleScope.launch {
            viewModel.quizzes.collect {
                adapter.setQuiz(it)
            }
        }

        lifecycleScope.launch {
            viewModel.profileUri.collect{
                Glide.with(requireContext())
                    .load(it)
                    .placeholder(R.drawable.ic_image)
                    .into(binding.ivImage)
            }
        }

    }

    private fun setupAdapter() {
        adapter = quizAddedAdapter(emptyList())
        adapter.listener = object : quizAddedAdapter.Listener {
            override fun onDelete(quiz: Quiz) {
                viewModel.delete(quiz)
            }


        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvQuizItem.adapter = adapter
        binding.rvQuizItem.layoutManager = layoutManager
    }
}