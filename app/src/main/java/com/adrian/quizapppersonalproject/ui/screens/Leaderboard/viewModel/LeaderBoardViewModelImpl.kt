package com.adrian.quizapppersonalproject.ui.screens.Leaderboard.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrian.quizapppersonalproject.data.model.Result
import com.adrian.quizapppersonalproject.data.repo.ResultRepo
import com.adrian.quizapppersonalproject.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderBoardViewModelImpl @Inject constructor(
    private val resultRepo: ResultRepo
) : BaseViewModel(), LeaderBoardViewModel {

    private val _result = MutableStateFlow<List<Result>>(emptyList())
    val result: StateFlow<List<Result>> = _result

    init {
        getResult()
    }

    override fun getResult() {
        viewModelScope.launch(Dispatchers.IO) {
//            errorHandler {
//                resultRepo.getResult()
//            }?.collect{
//                _result.value = it
//            }
            resultRepo.getResult().collect{
                _result.value = it
                Log.d("debugging", it.toString())
            }
        }
    }


}