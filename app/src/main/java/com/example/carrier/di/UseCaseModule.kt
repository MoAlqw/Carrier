package com.example.carrier.di

import com.example.domain.repository.CompanyRepository
import com.example.domain.repository.ExpenseRepository
import com.example.domain.repository.TripRepository
import com.example.domain.repository.VehicleRepository
import com.example.domain.usecase.CreateCompanyUseCase
import com.example.domain.usecase.CreateExpenseUseCase
import com.example.domain.usecase.CreateTripUseCase
import com.example.domain.usecase.CreateVehicleUseCase
import com.example.domain.usecase.GetClosedTripsWithExpensesAndVehicleUseCase
import com.example.domain.usecase.GetClosedTripsWithExpensesUseCase
import com.example.domain.usecase.GetCompanyUseCase
import com.example.domain.usecase.GetExpenseCategoriesAnalyticsUseCase
import com.example.domain.usecase.GetInProgressTripsWithExpensesAndVehicleUseCase
import com.example.domain.usecase.GetPeriodAnalyticsUseCase
import com.example.domain.usecase.GetTripWithExpensesAndVehicleByIdUseCase
import com.example.domain.usecase.GetTripWithExpensesByIdUseCase
import com.example.domain.usecase.GetTripsSummaryUseCase
import com.example.domain.usecase.GetTripsWithExpensesAndVehicleUseCase
import com.example.domain.usecase.GetVehicleByIdUseCase
import com.example.domain.usecase.GetVehiclesUseCase
import com.example.domain.usecase.UpdateStatusTripUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetVehiclesUseCase(
        vehicleRepository: VehicleRepository
    ): GetVehiclesUseCase {
        return GetVehiclesUseCase(vehicleRepository)
    }

    @Provides
    fun provideGetCompanyUseCase(
        companyRepository: CompanyRepository
    ): GetCompanyUseCase {
        return GetCompanyUseCase(companyRepository)
    }

    @Provides
    fun provideCreateCompanyUseCase(
        companyRepository: CompanyRepository
    ): CreateCompanyUseCase {
        return CreateCompanyUseCase(companyRepository)
    }

    @Provides
    fun provideCreateVehicleUseCase(
        vehicleRepository: VehicleRepository
    ): CreateVehicleUseCase {
        return CreateVehicleUseCase(vehicleRepository)
    }

    @Provides
    fun provideCreateTripUseCase(
        tripRepository: TripRepository
    ): CreateTripUseCase {
        return CreateTripUseCase(tripRepository)
    }

    @Provides
    fun provideGetVehicleByIdUseCase(
        vehicleRepository: VehicleRepository
    ): GetVehicleByIdUseCase {
        return GetVehicleByIdUseCase(vehicleRepository)
    }

    @Provides
    fun provideGetTripWithExpensesByIdUseCase(
        tripRepository: TripRepository
    ): GetTripWithExpensesByIdUseCase {
        return GetTripWithExpensesByIdUseCase(tripRepository)
    }

    @Provides
    fun provideCreateExpenseUseCase(
        expenseRepository: ExpenseRepository
    ): CreateExpenseUseCase {
        return CreateExpenseUseCase(expenseRepository)
    }

    @Provides
    fun provideUpdateTripUseCase(
        tripRepository: TripRepository
    ): UpdateStatusTripUseCase {
        return UpdateStatusTripUseCase(tripRepository)
    }

    @Provides
    fun provideGetClosedTripsWithExpensesUseCase(
        tripRepository: TripRepository
    ): GetClosedTripsWithExpensesUseCase {
        return GetClosedTripsWithExpensesUseCase(tripRepository)
    }

    @Provides
    fun provideGetSummaryUseCase(): GetTripsSummaryUseCase {
        return GetTripsSummaryUseCase()
    }

    @Provides
    fun provideGetPeriodAnalyticsUseCase(): GetPeriodAnalyticsUseCase {
        return GetPeriodAnalyticsUseCase()
    }

    @Provides
    fun provideGetExpenseCategoriesAnalyticsUseCase(): GetExpenseCategoriesAnalyticsUseCase {
        return GetExpenseCategoriesAnalyticsUseCase()
    }

    @Provides
    fun provideGetTripsWithExpensesAndVehicleUseCase(
        tripRepository: TripRepository
    ): GetTripsWithExpensesAndVehicleUseCase {
        return GetTripsWithExpensesAndVehicleUseCase(tripRepository)
    }

    @Provides
    fun provideGetTripWithExpensesAndVehicleByIdUseCase(
        tripRepository: TripRepository
    ): GetTripWithExpensesAndVehicleByIdUseCase {
        return GetTripWithExpensesAndVehicleByIdUseCase(tripRepository)
    }

    @Provides
    fun provideGetClosedTripsWithExpensesAndVehicleUseCase(
        tripRepository: TripRepository
    ): GetClosedTripsWithExpensesAndVehicleUseCase {
        return GetClosedTripsWithExpensesAndVehicleUseCase(tripRepository)
    }

    @Provides
    fun provideGetInProgressTripsWithExpensesAndVehicleUseCase(
        tripRepository: TripRepository
    ): GetInProgressTripsWithExpensesAndVehicleUseCase {
        return GetInProgressTripsWithExpensesAndVehicleUseCase(tripRepository)
    }
}