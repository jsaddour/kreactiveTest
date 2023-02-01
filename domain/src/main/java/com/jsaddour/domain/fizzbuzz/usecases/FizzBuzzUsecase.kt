package com.jsaddour.domain.fizzbuzz.usecases

import com.jsaddour.domain.fizzbuzz.models.*

class FizzBuzzUsecase {
    suspend fun execute(inputs: FBInputs): FBResults {
        checkInputs(inputs)?.let { error -> return error }

        val result = Success(
            (1..inputs.limit).map { index ->
                when {
                    index.isMultiple(inputs.firstInteger * inputs.secondInteger) -> {
                        "${inputs.firstWord}${inputs.secondWord}"
                    }
                    index.isMultiple(inputs.firstInteger) -> {
                        inputs.firstWord
                    }
                    index.isMultiple(inputs.secondInteger) -> {
                        inputs.secondWord
                    }
                    else -> {
                        index.toString()
                    }
                }

            }
        )
        return result
    }

    private fun Int.isMultiple(input: Int): Boolean {
        return this % input == 0
    }

    private fun checkInputs(inputs: FBInputs): FBError? {
        return when {
            inputs.firstInteger > inputs.limit -> InvalidFirstInteger
            inputs.secondInteger > inputs.limit -> InvalidSecondInteger
            else -> null
        }
    }
}