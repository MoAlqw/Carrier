package com.example.carrier.model

import com.example.domain.model.TripFinanceCalculator
import com.example.domain.model.TripStatus
import com.example.domain.model.TripWithExpensesAndVehicle
import java.time.Instant

data class TripItemUi(
    val id: Long,
    val route: String,
    val client: String,
    val date: Instant,
    val km: Long,
    val vehicle: VehicleItemUi,
    val amount: Long,
    val status: TripStatus,
    val expenses: List<ExpenseItemUi>
) {

    val totalExpenses: Long
        get() = expenses.sumOf { it.amount }

    val grossProfit: Long
        get() = TripFinanceCalculator.grossProfit(amount, totalExpenses)

    val netProfit: Long
        get() = TripFinanceCalculator.netProfit(grossProfit)

    val tax: Long
        get() = TripFinanceCalculator.tax(grossProfit)

    val profitability: Long
        get() = TripFinanceCalculator.profitability(amount, netProfit)
}

fun TripWithExpensesAndVehicle.toTripItemUi() = TripItemUi(
    id = trip.id,
    route = trip.route,
    client = trip.client,
    date = trip.date,
    km = trip.km,
    vehicle = vehicle.toVehicleUi(),
    amount = trip.amount,
    status = trip.status,
    expenses = expenses.map { it.toExpenseItemUi() }
)