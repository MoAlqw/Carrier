package com.example.carrier.common

import com.example.domain.model.Expense
import com.example.domain.model.ExpenseCategory
import com.example.carrier.utils.DateFormatter

data class ExpenseItemUi(
    val category: ExpenseCategory,
    val name: String,
    val amount: Long,
    val date: String
)

fun Expense.toExpenseItemUi() = ExpenseItemUi(
    category = category,
    name = name,
    amount = amount,
    date = DateFormatter.format(date.toEpochMilli())
)