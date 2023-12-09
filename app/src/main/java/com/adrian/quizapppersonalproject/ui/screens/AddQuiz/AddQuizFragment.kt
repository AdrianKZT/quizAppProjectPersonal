package com.adrian.quizapppersonalproject.ui.screens.AddQuiz


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.adrian.quizapppersonalproject.R
import com.adrian.quizapppersonalproject.databinding.FragmentAddQuizBinding
import com.adrian.quizapppersonalproject.ui.screens.AddQuiz.viewModel.AddQuizViewModelImpl
import com.adrian.quizapppersonalproject.ui.screens.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

@AndroidEntryPoint
class AddQuizFragment : BaseFragment<FragmentAddQuizBinding>() {
    override val viewModel: AddQuizViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents(view: View) {
        super.setupUIComponents(view)

        binding.run {
            btnAddQuiz.setOnClickListener {
                val prefixText = "#"
                val title = etTitle.text.toString()
                val quizId = etAddQuizId.text.toString()
                val time = autoCompleteTv.text.toString().toLongOrNull()

                if (title.isNotEmpty() && quizId.isNotEmpty()) {
                    if (quizId.startsWith(prefixText)) {
                        if (time != null) {
                            viewModel.addQuiz(title, quizId, time)
                        }
                    } else {
                        Snackbar.make(
                            requireView(),
                            "Quiz ID must start with '$prefixText' ",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                } else if (title.isNotEmpty() && quizId.isEmpty()) {
                    Snackbar.make(requireView(), "Please enter your Quiz ID", Snackbar.LENGTH_LONG)
                        .show()
                } else if (title.isEmpty() && quizId.isNotEmpty()) {
                    Snackbar.make(requireView(), "Please enter your title", Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    Snackbar.make(
                        requireView(),
                        "Please fill up all the boxes",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
            }
            val time = resources.getStringArray(R.array.time_in_seconds)
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_items, time)
            autoCompleteTv.setAdapter(arrayAdapter)

            ivAddCSV.setOnClickListener {
                getContent.launch("text/*")
            }
        }
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {

                val documentFile = DocumentFile.fromSingleUri(requireContext(), it)
                val originalFileName = documentFile?.name

                Log.d("debugging", "Original File Name: $originalFileName")

                binding.run {
                    tvShowFile.text = originalFileName ?: "Unknown File"
                }

                try {
                    val csvFile = requireActivity().contentResolver.openInputStream(it)
                    csvFile?.let {
                        val isr = InputStreamReader(csvFile)
                        BufferedReader(isr).readLines().let { lines ->
                            viewModel.readCsv(lines)
                        }
                    } ?: Log.e("debugging", "Failed to open input stream for csv")
                } catch (e: Exception) {
                    Log.e("debugging", "Error reading CSV file: ${e.message}")
                }


            }
        }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.finish.collect {
                val action = AddQuizFragmentDirections.toTeacherHome()
                navController.navigate(action)
            }
        }
    }
}