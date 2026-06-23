package com.example.domain.model

data class TripSummary(
    val tripsCount: Int,
    val revenue: Long,
    val expenses: Long,
    val profit: Long,
    val profitability: Long
)