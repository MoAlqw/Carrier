package com.example.data.db.converter

import androidx.room.TypeConverter
import com.example.domain.model.ExpenseCategory

class ExpenseConverter {

    @TypeConverter
    fun fromExpenseCategory(expense: ExpenseCategory): String {
        return expense.name
    }

    @TypeConverter
    fun toExpenseCategory(value: String): ExpenseCategory {
        return ExpenseCategory.valueOf(value)
    }
}