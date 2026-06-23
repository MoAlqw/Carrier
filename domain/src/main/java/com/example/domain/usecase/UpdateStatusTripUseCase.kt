package com.example.domain.usecase

import com.example.domain.model.TripStatus
import com.example.domain.repository.TripRepository

class UpdateStatusTripUseCase(
    private val tripRepository: TripRepository
) {
    suspend operator fun invoke(id: Long) {
        val trip = tripRepository.getTripById(id)
        trip.collect { tripValue ->
            tripValue?.let {
                tripRepository.updateTrip(tripValue.copy(status = TripStatus.CLOSED))
            }
        }
    }
}