package com.example.carrier.model

import com.example.domain.model.TripStatus
import com.example.domain.model.TripWithExpenses
import kotlin.math.roundToLong

data class ExpensesTripUi(
    val id: Long,
    val status: TripStatus,
    val amount: Long,
    val expenses: List<ExpenseItemUi>
) {
    val totalExpenses: Long
        get() = expenses.sumOf { it.amount }

    val tax: Long
        get() = ((amount - totalExpenses) * 0.12).roundToLong()

    val netProfit: Long
        get() = (amount - totalExpenses - tax)
}

fun TripWithExpenses.toExpensesTripUi() = ExpensesTripUi(
    id = trip.id,
    status = trip.status,
    amount = trip.amount,
    expenses = expenses.map { it.toExpenseItemUi() }
)