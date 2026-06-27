package com.example.domain.usecase

import com.example.domain.repository.TripRepository

class GetInProgressTripsWithExpensesAndVehicleUseCase(
    private val tripRepository: TripRepository
) {
    operator fun invoke() = tripRepository.getInProgressTripsWithExpensesAndVehicle()
}