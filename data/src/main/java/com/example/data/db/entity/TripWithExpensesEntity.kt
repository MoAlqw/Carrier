package com.example.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.domain.model.TripWithExpenses

data class TripWithExpensesEntity(

    @Embedded
    val trip: TripEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "tripId"
    )
    val expenses: List<ExpenseEntity>
)

fun TripWithExpensesEntity.toTripWithExpenses() = TripWithExpenses(
    trip = trip.toTrip(),
    expenses = expenses.map { it.toExpense() }
)