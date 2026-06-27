package com.example.carrier.validation.vehicle

import com.example.carrier.presentation.addvehicle.CreateVehicleFormState
import javax.inject.Inject

class VehicleValidator @Inject constructor() {

    fun validate(vehicle: CreateVehicleFormState): Set<VehicleValidationError> {
        val errors = mutableSetOf<VehicleValidationError>()

        if (vehicle.brand.isBlank()) {
            errors.add(VehicleValidationError.BrandEmpty)
        }

        if (vehicle.model.isBlank()) {
            errors.add(VehicleValidationError.ModelEmpty)
        }

        if (vehicle.plate.isBlank()) {
            errors.add(VehicleValidationError.PlateEmpty)
        } else if (!vehicle.plate.uppercase().matches(PLATE_REGEX)) {
            errors.add(VehicleValidationError.PlateInvalid)
        }

        val fuel = vehicle.fuelConsumption.toDoubleOrNull()

        if (vehicle.fuelConsumption.isBlank()) {
            errors.add(VehicleValidationError.FuelConsumptionEmpty)
        } else if (fuel == null || fuel <= 0) {
            errors.add(VehicleValidationError.FuelConsumptionInvalid)
        }

        return errors
    }

    private companion object {
        val PLATE_REGEX = Regex("""\d{3}\s?[A-Z]{3}\s?\d{2}""")
    }
}