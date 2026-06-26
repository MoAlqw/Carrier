package com.example.domain.usecase

import com.example.domain.model.TripFinanceCalculator
import com.example.domain.model.TripWithExpenses
import com.example.domain.model.TripSummary

class GetTripsSummaryUseCase{
    operator fun invoke(trips: List<TripWithExpenses>): TripSummary {

        val revenue = trips.sumOf { it.trip.amount }
        val expenses = trips.sumOf { tripDetails ->
            tripDetails.expenses.sumOf { it.amount }
        }

        val grossProfit = TripFinanceCalculator.grossProfit(revenue, expenses)
        val netProfit = TripFinanceCalculator.netProfit(grossProfit)
        val profitability = TripFinanceCalculator.profitability(revenue, netProfit)

        return TripSummary(
            tripsCount = trips.size,
            revenue = revenue,
            expenses = expenses,
            profit = grossProfit,
            profitability = profitability
        )
    }
}