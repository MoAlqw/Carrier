package com.example.carrier.validation.vehicle

import com.example.carrier.validation.ValidationError

interface VehicleValidationError: ValidationError {
    data object BrandEmpty : VehicleValidationError
    data object ModelEmpty : VehicleValidationError
    data object PlateEmpty : VehicleValidationError
    data object PlateInvalid : VehicleValidationError
    data object FuelConsumptionEmpty : VehicleValidationError
    data object FuelConsumptionInvalid : VehicleValidationError
}