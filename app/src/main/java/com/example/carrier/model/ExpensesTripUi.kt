package com.example.carrier.model

import com.example.domain.model.TripFinanceCalculator
import com.example.domain.model.TripStatus
import com.example.domain.model.TripWithExpenses

data class ExpensesTripUi(
    val id: Long,
    val status: TripStatus,
    val amount: Long,
    val expenses: List<ExpenseItemUi>
) {
    val totalExpenses: Long
        get() = expenses.sumOf { it.amount }

    private val grossProfit: Long
        get() = TripFinanceCalculator.grossProfit(amount, totalExpenses)

    val tax: Long
        get() = TripFinanceCalculator.tax(grossProfit)

    val netProfit: Long
        get() = TripFinanceCalculator.netProfit(grossProfit)
}

fun TripWithExpenses.toExpensesTripUi() = ExpensesTripUi(
    id = trip.id,
    status = trip.status,
    amount = trip.amount,
    expenses = expenses.map { it.toExpenseItemUi() }
)