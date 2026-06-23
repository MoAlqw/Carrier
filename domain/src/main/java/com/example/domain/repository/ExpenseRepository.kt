package com.example.domain.repository

import com.example.domain.model.Expense

interface ExpenseRepository {
    suspend fun createExpense(expense: Expense)
}