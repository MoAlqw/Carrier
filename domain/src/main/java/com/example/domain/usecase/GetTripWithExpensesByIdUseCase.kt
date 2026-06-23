package com.example.domain.usecase

import com.example.domain.repository.TripRepository

class GetTripWithExpensesByIdUseCase(
    private val tripRepository: TripRepository
) {
    operator fun invoke(id: Long) = tripRepository.getTripWithExpensesById(id)
}