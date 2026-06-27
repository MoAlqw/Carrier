package com.example.domain.repository

import com.example.domain.model.Trip
import com.example.domain.model.TripWithExpenses
import com.example.domain.model.TripWithExpensesAndVehicle
import kotlinx.coroutines.flow.Flow

interface TripRepository {
    fun getTripById(id: Long): Flow<Trip?>
    suspend fun createTrip(trip: Trip)
    suspend fun updateTrip(trip: Trip)
    fun getTripsWithExpensesAndVehicle(): Flow<List<TripWithExpensesAndVehicle>>
    fun getTripWithExpensesAndVehicleById(id: Long): Flow<TripWithExpensesAndVehicle>
    fun getTripWithExpensesById(id: Long): Flow<TripWithExpenses>
    fun getClosedTripsWithExpensesAndVehicle(): Flow<List<TripWithExpensesAndVehicle>>
    fun getClosedTripsWithExpenses(): Flow<List<TripWithExpenses>>
    fun getInProgressTripsWithExpensesAndVehicle(): Flow<List<TripWithExpensesAndVehicle>>
}