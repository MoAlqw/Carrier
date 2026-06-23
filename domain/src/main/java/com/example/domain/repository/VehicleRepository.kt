package com.example.domain.repository

import com.example.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    fun getVehicles(): Flow<List<Vehicle>>
    suspend fun getVehicleById(id: Long): Vehicle?
    suspend fun createVehicle(vehicle: Vehicle)
}