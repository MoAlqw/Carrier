package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.dao.CompanyDao
import com.example.data.db.dao.DriverDao
import com.example.data.db.dao.ExpenseDao
import com.example.data.db.dao.TripDao
import com.example.data.db.dao.VehicleDao
import com.example.data.db.entity.CompanyEntity
import com.example.data.db.entity.DriverEntity
import com.example.data.db.entity.ExpenseEntity
import com.example.data.db.entity.TripEntity
import com.example.data.db.entity.VehicleEntity

@Database(
    entities = [
        CompanyEntity::class,
        DriverEntity::class,
        VehicleEntity::class,
        TripEntity::class,
        ExpenseEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun companyDao(): CompanyDao
    abstract fun driverDao(): DriverDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun tripDao(): TripDao
    abstract fun expenseDao(): ExpenseDao
}