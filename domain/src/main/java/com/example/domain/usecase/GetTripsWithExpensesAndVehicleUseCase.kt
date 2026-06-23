package com.example.domain.usecase

import com.example.domain.repository.TripRepository

class GetTripsWithExpensesAndVehicleUseCase(
    private val tripRepository: TripRepository
) {
    operator fun invoke() = tripRepository.getTripsWithExpensesAndVehicle()
}