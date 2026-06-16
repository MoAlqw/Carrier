package com.example.domain.repository

import com.example.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    suspend fun getVehicles(): Flow<List<Vehicle>>
}