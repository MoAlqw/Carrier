package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.data.db.entity.TripEntity
import com.example.data.db.entity.TripWithExpensesAndVehicleEntity
import com.example.data.db.entity.TripWithExpensesEntity
import com.example.domain.model.TripWithExpenses
import com.example.domain.model.TripWithExpensesAndVehicle
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {

    @Query("SELECT * FROM trips WHERE id = :id")
    fun getTrip(id: Long): Flow<TripEntity?>

    @Insert
    suspend fun insert(trip: TripEntity)

    @Update
    suspend fun update(trip: TripEntity)

    @Transaction
    @Query("SELECT * FROM trips")
    fun getTripsWithVehicleAndExpenses(): Flow<List<TripWithExpensesAndVehicleEntity>>

    @Transaction
    @Query("SELECT * FROM trips")
    fun getTripsWithExpenses(): Flow<List<TripWithExpensesEntity>>

    @Transaction
    @Query("SELECT * FROM trips WHERE id = :id")
    fun getTripWithExpensesById(id: Long): Flow<TripWithExpensesEntity>

    @Transaction
    @Query("""
        SELECT *
        FROM trips
        WHERE status = "CLOSED"
        ORDER BY date DESC
    """)
    fun getClosedTripsWithExpenses(): Flow<List<TripWithExpensesEntity>>
}