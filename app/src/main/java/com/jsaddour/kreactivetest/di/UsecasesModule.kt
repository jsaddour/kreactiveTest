package com.jsaddour.kreactivetest.di

import com.jsaddour.domain.fizzbuzz.usecases.FizzBuzzUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UsecasesModule {

    @Provides
    fun provideFizzBuzzUsecase(): FizzBuzzUsecase = FizzBuzzUsecase()
}