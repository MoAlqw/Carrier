package com.example.domain.usecase

import com.example.domain.repository.TripRepository

class GetClosedTripsWithExpensesUseCase(
    private val tripRepository: TripRepository
) {
    operator fun invoke() = tripRepository.getClosedTripsWithExpenses()
}