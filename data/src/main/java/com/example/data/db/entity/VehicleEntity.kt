package com.example.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Vehicle

@Entity(tableName = "vehicles")
data class VehicleEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val brand: String,
    val model: String,
    val plate: String,
    val fuelConsumption: Double
)

fun VehicleEntity.toVehicle() = Vehicle(
    id = id,
    brand = brand,
    model = model,
    plate = plate,
    fuelConsumption = fuelConsumption
)

fun Vehicle.toVehicleEntity() = VehicleEntity(
    id = id,
    brand = brand,
    model = model,
    plate = plate,
    fuelConsumption = fuelConsumption
)