package com.example.data.di

import com.example.data.repository.CompanyRepositoryImpl
import com.example.data.repository.ExpenseRepositoryImpl
import com.example.data.repository.TripRepositoryImpl
import com.example.data.repository.VehicleRepositoryImpl
import com.example.domain.repository.CompanyRepository
import com.example.domain.repository.ExpenseRepository
import com.example.domain.repository.TripRepository
import com.example.domain.repository.VehicleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindVehicleRepository(
        impl: VehicleRepositoryImpl
    ): VehicleRepository

    @Binds
    abstract fun bindCompanyRepository(
        impl: CompanyRepositoryImpl
    ): CompanyRepository

    @Binds
    abstract fun bindTripRepository(
        impl: TripRepositoryImpl
    ): TripRepository

    @Binds
    abstract fun bindExpenseRepository(
        impl: ExpenseRepositoryImpl
    ): ExpenseRepository
}