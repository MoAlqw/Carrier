package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.AppDatabase
import com.example.data.db.dao.CompanyDao
import com.example.data.db.dao.ExpenseDao
import com.example.data.db.dao.TripDao
import com.example.data.db.dao.VehicleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "carrier.db"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun provideVehicleDao(
        database: AppDatabase
    ): VehicleDao {
        return database.vehicleDao()
    }

    @Provides
    fun provideInspectionDao(
        database: AppDatabase
    ): CompanyDao {
        return database.companyDao()
    }

    @Provides
    fun provideTripDao(
        database: AppDatabase
    ): TripDao {
        return database.tripDao()
    }

    @Provides
    fun provideExpenseDao(
        database: AppDatabase
    ): ExpenseDao {
        return database.expenseDao()
    }
}