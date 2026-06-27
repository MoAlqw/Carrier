package com.example.data.repository

import com.example.data.db.dao.TripDao
import com.example.data.db.entity.toEntity
import com.example.data.db.entity.toTrip
import com.example.data.db.entity.toTripWithExpenses
import com.example.data.db.entity.toTripWithExpensesAndVehicle
import com.example.domain.model.Trip
import com.example.domain.model.TripWithExpenses
import com.example.domain.model.TripWithExpensesAndVehicle
import com.example.domain.repository.TripRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class TripRepositoryImpl @Inject constructor(
    private val tripDao: TripDao
) : TripRepository {

    override fun getTripById(id: Long): Flow<Trip?> {
        return tripDao.getTrip(id).map { it?.toTrip() }
    }

    override suspend fun createTrip(trip: Trip) {
        tripDao.insert(trip.toEntity())
    }

    override suspend fun updateTrip(trip: Trip) {
        tripDao.update(trip.toEntity())
    }

    override fun getTripsWithExpensesAndVehicle(): Flow<List<TripWithExpensesAndVehicle>> {
        return tripDao.getTripsWithVehicleAndExpenses().map {
            it.map { tripWithExpensesAndVehicle ->
                tripWithExpensesAndVehicle.toTripWithExpensesAndVehicle()
            }
        }
    }

    override fun getTripWithExpensesById(id: Long): Flow<TripWithExpenses> {
        return tripDao.getTripWithExpensesById(id).map {
            it.toTripWithExpenses()
        }
    }

    override fun getTripWithExpensesAndVehicleById(id: Long): Flow<TripWithExpensesAndVehicle> {
        return tripDao.getTripWithExpensesAndVehicleById(id).map {
            it.toTripWithExpensesAndVehicle()
        }
    }

    override fun getClosedTripsWithExpenses(): Flow<List<TripWithExpenses>> {
        return tripDao.getClosedTripsWithExpenses().map {
            it.map { tripWithExpenses ->
                tripWithExpenses.toTripWithExpenses()
            }
        }
    }

    override fun getClosedTripsWithExpensesAndVehicle(): Flow<List<TripWithExpensesAndVehicle>> {
        return tripDao.getClosedTripsWithVehicleAndExpenses().map {
            it.map { tripWithExpensesAndVehicle ->
                tripWithExpensesAndVehicle.toTripWithExpensesAndVehicle()
            }
        }
    }

    override fun getInProgressTripsWithExpensesAndVehicle(): Flow<List<TripWithExpensesAndVehicle>> {
        return tripDao.getInProgressTripsWithVehicleAndExpenses().map {
            it.map { tripWithExpensesAndVehicle ->
                tripWithExpensesAndVehicle.toTripWithExpensesAndVehicle()
            }
        }
    }
}