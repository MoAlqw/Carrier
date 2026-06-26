package com.example.carrier.model.analytics

import com.example.domain.model.CategoryAnalytics
import com.example.domain.model.PeriodAnalytics
import com.example.domain.model.TripFinanceCalculator
import com.example.domain.model.TripSummary

data class AnalyticsUiState(
    val summary: TripSummary,
    val categories: List<CategoryAnalytics>,
    val periods: List<PeriodAnalytics>
) {
    val tax: Long
        get() = TripFinanceCalculator.tax(summary.profit)
}