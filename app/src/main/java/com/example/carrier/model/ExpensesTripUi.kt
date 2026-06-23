package com.example.carrier.model

import com.example.domain.model.Expense
import com.example.domain.model.TripStatus
import kotlin.math.roundToLong

data class ExpensesTripUi(
    val id: Long,
    val status: TripStatus,
    val amount: Long,
    val expenses: List<Expense>
) {
    val totalExpenses: Long
        get() = expenses.sumOf { it.amount }

    val tax: Long
        get() = ((amount - totalExpenses) * 0.12).roundToLong()

    val netProfit: Long
        get() = (amount - totalExpenses - tax)
}