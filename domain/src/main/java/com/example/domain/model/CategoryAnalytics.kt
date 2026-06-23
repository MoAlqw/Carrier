package com.example.domain.model

data class CategoryAnalytics(
    val category: ExpenseCategory,
    val amount: Long,
    val percent: Long
)