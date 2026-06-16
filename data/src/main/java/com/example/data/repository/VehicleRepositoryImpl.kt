package com.example.data.repository

import com.example.data.db.dao.VehicleDao
import com.example.data.db.entity.toVehicle
import com.example.domain.model.Vehicle
import com.example.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val vehicleDao: VehicleDao
) : VehicleRepository {
    override suspend fun getVehicles(): Flow<List<Vehicle>> {
        return vehicleDao.getVehicles().map { vehicleEntities ->
            vehicleEntities.map { it.toVehicle() }
        }
    }
}