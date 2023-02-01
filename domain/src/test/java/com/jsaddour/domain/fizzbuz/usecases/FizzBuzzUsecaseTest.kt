package com.jsaddour.domain.fizzbuz.usecases

import com.jsaddour.domain.fizzbuzz.models.FBInputs
import com.jsaddour.domain.fizzbuzz.models.InvalidFirstInteger
import com.jsaddour.domain.fizzbuzz.models.InvalidSecondInteger
import com.jsaddour.domain.fizzbuzz.models.Success
import com.jsaddour.domain.fizzbuzz.usecases.FizzBuzzUsecase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FizzBuzzUsecaseTest {

    @Test
    fun `should return InvalidFirstInteger FBError`() = runBlocking {
        val inputs = FBInputs(42, 5, "fizz", "buzz", 16)
        val result = FizzBuzzUsecase().execute(inputs)

        assert(result is InvalidFirstInteger)

    }

    @Test
    fun `should return InvalidSecondInteger FBError`() = runBlocking {
        val inputs = FBInputs(3, 42, "fizz", "buzz", 16)
        val result = FizzBuzzUsecase().execute(inputs)

        assert(result is InvalidSecondInteger)

    }

    @Test
    fun `should return fizzBuzz list`() = runBlocking {
        val inputs = FBInputs(3, 5, "fizz", "buzz", 16)
        val result = FizzBuzzUsecase().execute(inputs)

        assert(result is Success)
        assertEquals(
            listOf(
                "1",
                "2",
                "fizz",
                "4",
                "buzz",
                "fizz",
                "7",
                "8",
                "fizz",
                "buzz",
                "11",
                "fizz",
                "13",
                "14",
                "fizzbuzz",
                "16"
            ),
            (result as Success).resultList
        )
    }
}