package com.jsaddour.kreactivetest.ui.results

import androidx.lifecycle.*
import com.jsaddour.kreactivetest.ui.results.ResultActivity.Companion.RESULT_EXTRA_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _resultViewState = MutableLiveData<List<String>>()
    val resultViewState: LiveData<List<String>> = _resultViewState

    val result : List<String>  = savedStateHandle.get(RESULT_EXTRA_KEY) ?: throw IllegalArgumentException(
            "RESULT_EXTRA_KEY should not be empty"
        )

    init {
        _resultViewState.value = result
    }

}