package com.example.domain.usecase

import com.example.domain.model.TripWithExpenses
import com.example.domain.model.TripSummary

class GetTripsSummaryUseCase{
    operator fun invoke(trips: List<TripWithExpenses>): TripSummary {

        val revenue = trips.sumOf { it.trip.amount }
        val expenses = trips.sumOf { tripDetails ->
            tripDetails.expenses.sumOf { it.amount }
        }

        val profit = revenue - expenses

        val profitability =
            if (revenue == 0L) 0
            else profit * 100 / revenue

        return TripSummary(
            tripsCount = trips.size,
            revenue = revenue,
            expenses = expenses,
            profit = profit,
            profitability = profitability
        )
    }
}