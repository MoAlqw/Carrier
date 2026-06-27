package com.example.domain.usecase

import com.example.domain.repository.TripRepository

class GetTripWithExpensesAndVehicleByIdUseCase(
    private val tripRepository: TripRepository
) {
    operator fun invoke(id: Long) = tripRepository.getTripWithExpensesAndVehicleById(id)
}