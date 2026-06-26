package com.example.carrier.model

import com.example.carrier.utils.DateFormatter
import com.example.domain.model.Expense
import com.example.domain.model.ExpenseCategory

data class CreateExpanseForm(
    var category: ExpenseCategory? = null,
    var name: String = "",
    var sum: String = "",
    var date: String? = ""
)

fun CreateExpanseForm.toExpense(): Expense {
    return Expense(
        id = 0,
        tripId = 0,
        category = category!!,
        name = name,
        amount = sum.toLong(),
        date = DateFormatter.toInstant(date!!.toLong())
    )
}