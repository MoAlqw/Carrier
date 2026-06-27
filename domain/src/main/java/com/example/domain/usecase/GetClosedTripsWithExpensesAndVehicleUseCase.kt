package com.example.domain.usecase

import com.example.domain.repository.TripRepository

class GetClosedTripsWithExpensesAndVehicleUseCase(
    private val tripRepository: TripRepository
) {
    operator fun invoke() = tripRepository.getClosedTripsWithExpensesAndVehicle()
}