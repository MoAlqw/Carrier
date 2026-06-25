package com.example.carrier.model

import com.example.domain.model.Vehicle

data class CreateVehicleFormState(
    val brand: String = "",
    val model: String = "",
    val plate: String = "",
    val fuelConsumption: String = ""
)

fun CreateVehicleFormState.toVehicle() = Vehicle(
    id = 0,
    brand = brand,
    model = model,
    plate = plate,
    fuelConsumption = fuelConsumption.toDouble()
)