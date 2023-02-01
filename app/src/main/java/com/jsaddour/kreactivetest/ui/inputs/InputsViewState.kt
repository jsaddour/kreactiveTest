package com.jsaddour.kreactivetest.ui.inputs

import com.jsaddour.domain.fizzbuzz.models.FBError
import com.jsaddour.domain.fizzbuzz.models.FBInputs
import com.jsaddour.domain.fizzbuzz.models.InvalidFirstInteger
import com.jsaddour.domain.fizzbuzz.models.InvalidSecondInteger
import com.jsaddour.kreactivetest.R


data class InputsViewState(
    val result: Event<List<String>>?,
    val loading: Boolean,
    val firstFielderror: Event<Int>?,
    val secondFielderror: Event<Int>?,
    val onFirstIntegerChange: (value: String) -> Unit,
    val onSecondIntegerChange: (value: String) -> Unit,
    val onFirstWordChange: (value: String) -> Unit,
    val onSecondWordChange: (value: String) -> Unit,
    val onLimitChange: (value: String) -> Unit,
    val onValidate: () -> Unit,
    val validateButtonEnabled: Boolean
) {
    companion object {
        const val FIRST_INTEGER_DEFAULT_VALUE = "3"
        const val SECOND_INTEGER_DEFAULT_VALUE = "5"
        const val FIRST_WORD_DEFAULT_VALUE = "fizz"
        const val SECOND_WORD_DEFAULT_VALUE = "buzz"
        const val LIMIT_DEFAULT_VALUE = "16"

        fun firstState(
            onFirstIntegerChange: (value: String) -> Unit,
            onSecondIntegerChange: (value: String) -> Unit,
            onFirstWordChange: (value: String) -> Unit,
            onSecondWordChange: (value: String) -> Unit,
            onLimitChange: (value: String) -> Unit,
            onValidate: () -> Unit
        ): InputsViewState =
            InputsViewState(
                null,
                false,
                null,
                null,
                onFirstIntegerChange,
                onSecondIntegerChange,
                onFirstWordChange,
                onSecondWordChange,
                onLimitChange,
                onValidate,
                true
            )
    }

    fun loading(): InputsViewState =
        copy(loading = true, firstFielderror = null, secondFielderror = null)

    fun error(newError: FBError?): InputsViewState {
        return when (newError) {
            InvalidFirstInteger -> {
                copy(
                    loading = false,
                    firstFielderror = Event(R.string.first_field_error)
                )
            }
            InvalidSecondInteger -> {
                copy(
                    loading = false,
                    secondFielderror = Event(R.string.second_field_error)
                )
            }
            null -> {
                copy(
                    loading = false,
                    firstFielderror = null,
                    secondFielderror = null
                )
            }
        }
    }

    fun validate(newResult: List<String>): InputsViewState = copy(
        loading = false,
        firstFielderror = null,
        secondFielderror = null,
        result = Event(newResult)
    )

    fun refreshValidateButton(inputs: Inputs): InputsViewState {
        val enabled = inputs.firstInteger.isNotEmpty() && inputs.secondInteger.isNotEmpty()
                && inputs.firstWord.isNotEmpty() && inputs.secondWord.isNotEmpty()
                && inputs.limit.isNotEmpty()
        return copy(validateButtonEnabled = enabled)
    }


    class Event<out T>(private val content: T) {
        private var consumed = false


        fun getContent(): T? {
            return if (consumed) {
                null
            } else {
                consumed = true
                content
            }
        }
    }

    data class Inputs(
        var firstInteger: String = FIRST_INTEGER_DEFAULT_VALUE,
        var secondInteger: String = SECOND_INTEGER_DEFAULT_VALUE,
        var firstWord: String = FIRST_WORD_DEFAULT_VALUE,
        var secondWord: String = SECOND_WORD_DEFAULT_VALUE,
        var limit: String = LIMIT_DEFAULT_VALUE,
    )


}

fun InputsViewState.Inputs.toModel() =
    FBInputs(firstInteger.toInt(), secondInteger.toInt(), firstWord, secondWord, limit.toInt())
