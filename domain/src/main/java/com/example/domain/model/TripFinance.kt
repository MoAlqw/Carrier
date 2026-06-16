package com.example.domain.model

data class TripFinance(
    val revenue: Long,
    val plannedFuelLiters: Double,
    val estimatedFuelCost: Long,
    val totalExpenses: Long,
    val grossProfit: Long,
    val netProfit: Long,
    val profitability: Double
)