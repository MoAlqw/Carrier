package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.db.entity.DriverEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DriverDao {

    @Query("SELECT * FROM drivers WHERE id = 1")
    fun getDriver(): Flow<DriverEntity?>

    @Insert
    suspend fun insert(driver: DriverEntity)
}