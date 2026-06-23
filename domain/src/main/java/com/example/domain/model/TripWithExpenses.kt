package com.example.domain.model

data class TripWithExpenses(
    val trip: Trip,
    val expenses: List<Expense>
)