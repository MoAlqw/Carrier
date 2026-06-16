package com.example.domain.model

import java.time.Instant

data class Expense(
    val id: Long,
    val tripId: Long,
    val category: ExpenseCategory,
    val name: String,
    val amount: Long,
    val date: Instant
)