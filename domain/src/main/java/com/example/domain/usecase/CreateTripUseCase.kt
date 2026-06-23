package com.example.domain.usecase

import com.example.domain.model.Trip
import com.example.domain.repository.TripRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateTripUseCase(
    private val tripsRepository: TripRepository
) {
    suspend operator fun invoke(trip: Trip) {
        withContext(Dispatchers.IO) {
            tripsRepository.createTrip(trip)
        }
    }
}