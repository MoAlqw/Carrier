package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.db.converter.ExpenseConverter
import com.example.data.db.converter.InstantConverter
import com.example.data.db.converter.TripStatusConverter
import com.example.data.db.dao.CompanyDao
import com.example.data.db.dao.ExpenseDao
import com.example.data.db.dao.TripDao
import com.example.data.db.dao.VehicleDao
import com.example.data.db.entity.CompanyEntity
import com.example.data.db.entity.ExpenseEntity
import com.example.data.db.entity.TripEntity
import com.example.data.db.entity.VehicleEntity

@Database(
    entities = [
        CompanyEntity::class,
        VehicleEntity::class,
        TripEntity::class,
        ExpenseEntity::class
    ],
    version = 7,
    exportSchema = true
)
@TypeConverters(
    TripStatusConverter::class,
    InstantConverter::class,
    ExpenseConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun companyDao(): CompanyDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun tripDao(): TripDao
    abstract fun expenseDao(): ExpenseDao
}