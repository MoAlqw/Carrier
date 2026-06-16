package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.data.db.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses WHERE tripId = :tripId")
    fun getExpensesByTrip(tripId: Long): Flow<List<ExpenseEntity>>

    @Insert
    suspend fun insert(expense: ExpenseEntity)
}