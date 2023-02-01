package com.jsaddour.kreactivetest.ui.inputs


import com.jsaddour.domain.fizzbuzz.models.InvalidFirstInteger
import junit.framework.TestCase.assertEquals
import org.junit.Test


class InputsViewStateTest {

    private val firstState = InputsViewState.firstState({}, {}, {}, {}, {}, {})

    @Test
    fun `should return loading state`() {
        val loadingState = firstState.loading()
        assertEquals(
            firstState.copy(loading = true, firstFielderror = null, secondFielderror = null),
            loadingState,
        )
    }

    @Test
    fun `should return error state`() {
        val errorState = firstState.error(InvalidFirstInteger)
        assertEquals(
            false,
            errorState.loading,
        )
        assert(
            errorState.firstFielderror?.getContent() is Int,
        )
        //check if error event is consumed when get content second time
        assertEquals(
            null,
            errorState.firstFielderror?.getContent(),
        )
    }

    @Test
    fun `should return validate state`() {
        val results = listOf("fizz", "buzz")
        val validateState = firstState.validate(results)
        assertEquals(
            false,
            validateState.loading

        )
        assertEquals(
            null,
            validateState.firstFielderror
        )
        assertEquals(
            null,
            validateState.secondFielderror
        )
        assertEquals(
            results,
            validateState.result?.getContent(),
        )
        //check if error event is consumed when get content second time
        assertEquals(
            null,
            validateState.result?.getContent(),
        )
    }
}