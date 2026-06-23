package com.example.carrier.model

import com.example.domain.model.Expense
import com.example.domain.model.TripStatus
import com.example.domain.model.TripWithExpensesAndVehicle
import java.time.Instant

data class TripItemUi(
    val id: Long,
    val route: String,
    val client: String,
    val date: Instant,
    val km: Long,
    // VehicleUi
    val vehiclePlate: String,
    val amount: Long,
    val status: TripStatus,
    // ExpenseUi
    val expenses: List<Expense>
) {

    val totalExpenses: Long
        get() = expenses.sumOf { it.amount }

    val grossProfit: Long
        get() = amount - totalExpenses

    val netProfit: Long
        get() = amount - totalExpenses - tax

    val tax: Long
        get() = (amount - totalExpenses) * 12 / 100

    val profitability: Long
        get() = (amount - totalExpenses - tax) * 100 / amount
}

fun TripWithExpensesAndVehicle.toTripItemUi() = TripItemUi(
    id = trip.id,
    route = trip.route,
    client = trip.client,
    date = trip.date,
    km = trip.km,
    vehiclePlate = "${vehicle.brand} ${vehicle.model} · ${vehicle.plate}",
    amount = trip.amount,
    status = trip.status,
    expenses = expenses
)