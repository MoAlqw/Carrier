package com.example.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.domain.model.TripWithExpensesAndVehicle

data class TripWithExpensesAndVehicleEntity(

    @Embedded
    val trip: TripEntity,

    @Relation(
        parentColumn = "vehicleId",
        entityColumn = "id"
    )
    val vehicle: VehicleEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "tripId"
    )
    val expenses: List<ExpenseEntity>
)

fun TripWithExpensesAndVehicleEntity.toTripWithExpensesAndVehicle() =
    TripWithExpensesAndVehicle(
        trip = trip.toTrip(),
        vehicle = vehicle.toVehicle(),
        expenses = expenses.map { it.toExpense() }
    )