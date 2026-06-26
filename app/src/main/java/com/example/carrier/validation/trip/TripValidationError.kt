package com.example.carrier.validation.trip

import com.example.carrier.validation.ValidationError

interface TripValidationError: ValidationError {
    object DateEmpty: TripValidationError
    object RouteEmpty: TripValidationError
    object VehicleEmpty: TripValidationError
    object ClientEmpty: TripValidationError
    object AmountEmpty: TripValidationError
    object KmEmpty: TripValidationError
}