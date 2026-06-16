package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.db.entity.VehicleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {

    @Query("SELECT * FROM vehicles")
    fun getVehicles(): Flow<List<VehicleEntity>>

    @Insert
    suspend fun insert(vehicle: VehicleEntity)
}