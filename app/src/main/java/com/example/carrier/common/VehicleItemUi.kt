package com.example.carrier.common

import com.example.domain.model.Vehicle

data class VehicleItemUi(
    val brand: String,
    val model: String,
    val plate: String,
    val fuelConsumption: Double
)

fun Vehicle.toVehicleUi(): VehicleItemUi = VehicleItemUi(
    brand = brand,
    model = model,
    plate = plate,
    fuelConsumption = fuelConsumption
)