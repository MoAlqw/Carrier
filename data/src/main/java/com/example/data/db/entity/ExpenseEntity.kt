package com.example.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.domain.model.Expense
import com.example.domain.model.ExpenseCategory
import java.time.Instant

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = TripEntity::class,
            parentColumns = ["id"],
            childColumns = ["tripId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExpenseEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val tripId: Long,
    val category: ExpenseCategory,
    val name: String,
    val amount: Long,
    val date: Instant
)

fun ExpenseEntity.toExpense() = Expense(
    id = id,
    tripId = tripId,
    category = category,
    name = name,
    amount = amount,
    date = date
)

fun Expense.toEntity() = ExpenseEntity(
    id = id,
    tripId = tripId,
    category = category,
    name = name,
    amount = amount,
    date = date
)