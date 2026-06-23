package com.example.data.repository

import com.example.data.db.dao.ExpenseDao
import com.example.data.db.entity.toEntity
import com.example.domain.model.Expense
import com.example.domain.repository.ExpenseRepository
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
): ExpenseRepository {
    override suspend fun createExpense(expense: Expense) {
        expenseDao.insert(expense.toEntity())
    }
}