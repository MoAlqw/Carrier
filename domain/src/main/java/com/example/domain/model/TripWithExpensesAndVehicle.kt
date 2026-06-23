package com.example.domain.model

data class TripWithExpensesAndVehicle(
    val trip: Trip,
    val vehicle: Vehicle,
    val expenses: List<Expense>
)