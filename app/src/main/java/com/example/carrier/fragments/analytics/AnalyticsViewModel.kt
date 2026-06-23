package com.example.carrier.fragments.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrier.model.analytics.AnalyticsUiState
import com.example.domain.model.AnalyticsPeriod
import com.example.domain.usecase.GetClosedTripsWithExpensesUseCase
import com.example.domain.usecase.GetExpenseCategoriesAnalyticsUseCase
import com.example.domain.usecase.GetPeriodAnalyticsUseCase
import com.example.domain.usecase.GetTripsSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    getClosedTripsWithExpensesUseCase: GetClosedTripsWithExpensesUseCase,
    private val getSummary: GetTripsSummaryUseCase,
    private val getCategories: GetExpenseCategoriesAnalyticsUseCase,
    private val getPeriods: GetPeriodAnalyticsUseCase
) : ViewModel() {

    private val trips = getClosedTripsWithExpensesUseCase()
    private val period = MutableStateFlow(AnalyticsPeriod.MONTH)

    val uiState =
        combine(trips, period) { trips, periodType ->

            AnalyticsUiState(
                summary = getSummary(trips),
                categories = getCategories(trips),
                periods = getPeriods(trips, periodType)
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )

    fun changePeriod(period: AnalyticsPeriod) {
        if (period == this.period.value) return
        this.period.value = period
    }
}