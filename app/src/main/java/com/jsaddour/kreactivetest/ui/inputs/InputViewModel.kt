package com.jsaddour.kreactivetest.ui.inputs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsaddour.domain.fizzbuzz.models.FBError
import com.jsaddour.domain.fizzbuzz.models.Success
import com.jsaddour.domain.fizzbuzz.usecases.FizzBuzzUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor(
    private val fizzBuzzUsecase: FizzBuzzUsecase,
) : ViewModel() {

    private val inputs = InputsViewState.Inputs()

    private val _inputsViewState =
        MutableLiveData(
            InputsViewState.firstState(
                onFirstIntegerChange = { value ->
                    inputs.firstInteger = value
                    clearError()
                    refreshValidateButton()
                },
                onSecondIntegerChange = { value ->
                    inputs.secondInteger = value
                    clearError()
                    refreshValidateButton()
                },
                onFirstWordChange = { value ->
                    inputs.firstWord = value
                    refreshValidateButton()
                },
                onSecondWordChange = { value ->
                    inputs.secondWord = value
                    refreshValidateButton()
                },
                onLimitChange = { value ->
                    inputs.limit = value
                    refreshValidateButton()
                },
                onValidate = ::computeFizzBuzz
            )
        )
    val inputsViewState: LiveData<InputsViewState> = _inputsViewState


    private fun computeFizzBuzz() {
        viewModelScope.launch {
            _inputsViewState.value = inputsViewState.value?.loading()
            val result = fizzBuzzUsecase.execute(inputs.toModel())
            delay(2000L)
            if (result is FBError) {
                _inputsViewState.value = inputsViewState.value?.error(result)
            }
            if (result is Success) {

                _inputsViewState.value = inputsViewState.value?.validate( result.resultList)
            }
        }
    }

    private fun clearError() {
        _inputsViewState.value = inputsViewState.value?.error(null)
    }

    private fun refreshValidateButton() {
        _inputsViewState.value = inputsViewState.value?.refreshValidateButton(inputs)
    }

}