package com.jsaddour.domain.fizzbuzz.models

sealed class FBResults
sealed class FBError : FBResults()

data class Success(val resultList: List<String>) : FBResults()
object InvalidFirstInteger : FBError()
object InvalidSecondInteger : FBError()