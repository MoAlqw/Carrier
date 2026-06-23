package com.example.domain.usecase

import com.example.domain.repository.TripRepository

class GetTripsWithExpensesUseCase(
    private val tripRepository: TripRepository
) {
    operator fun invoke() = tripRepository.getTripsWithExpenses()
}