package com.example.domain.usecase

import com.example.domain.model.Vehicle
import com.example.domain.repository.VehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetVehiclesUseCase(
    private val vehicleRepository: VehicleRepository
) {
    operator fun invoke(): Flow<List<Vehicle>> {
        return vehicleRepository.getVehicles()
    }
}