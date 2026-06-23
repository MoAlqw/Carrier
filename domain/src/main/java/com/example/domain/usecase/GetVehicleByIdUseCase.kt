package com.example.domain.usecase

import com.example.domain.model.Vehicle
import com.example.domain.repository.VehicleRepository

class GetVehicleByIdUseCase(
    private val vehicleRepository: VehicleRepository
) {
    suspend operator fun invoke(id: Long): Vehicle? {
        return vehicleRepository.getVehicleById(id)
    }
}