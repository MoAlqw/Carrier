package com.example.carrier.validation.trip

import com.example.carrier.model.CreateTripForm
import javax.inject.Inject

class TripValidator @Inject constructor() {

    fun validate(trip: CreateTripForm): Set<TripValidationError> {
        val errors = mutableSetOf<TripValidationError>()
        if (trip.date == null) {
            errors.add(TripValidationError.DateEmpty)
        }

        if (trip.route.isBlank()) {
            errors.add(TripValidationError.RouteEmpty)
        }

        if (trip.vehicleId == null) {
            errors.add(TripValidationError.VehicleEmpty)
        }

        if (trip.client.isBlank()) {
            errors.add(TripValidationError.ClientEmpty)
        }

        if (trip.amount.isBlank()) {
            errors.add(TripValidationError.AmountEmpty)
        }

        if (trip.km.isBlank()) {
            errors.add(TripValidationError.KmEmpty)
        }

        return errors
    }
}