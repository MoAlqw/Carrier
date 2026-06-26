package com.example.domain.usecase

import com.example.domain.model.AnalyticsPeriod
import com.example.domain.model.PeriodAnalytics
import com.example.domain.model.TripFinanceCalculator
import com.example.domain.model.TripWithExpenses
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale

class GetPeriodAnalyticsUseCase {

    operator fun invoke(
        trips: List<TripWithExpenses>,
        type: AnalyticsPeriod
    ): List<PeriodAnalytics> {
        return when (type) {
            AnalyticsPeriod.MONTH -> buildMonths(trips)
            AnalyticsPeriod.YEAR -> buildYears(trips)
        }
    }

    private fun buildMonths(
        trips: List<TripWithExpenses>,
        year: Int = YearMonth.now().year
    ): List<PeriodAnalytics> {

        val zone = ZoneId.systemDefault()

        val grouped = trips.groupBy {
            YearMonth.from(it.trip.date.atZone(zone).toLocalDate())
        }

        val now = YearMonth.now()
        val startMonth = YearMonth.of(year, 1)
        val endMonth = if (year == now.year) now else YearMonth.of(year, 12)

        val result = mutableListOf<PeriodAnalytics>()
        var cursor = startMonth

        while (!cursor.isAfter(endMonth)) {
            val monthTrips = grouped[cursor].orEmpty()

            val revenue = monthTrips.sumOf { it.trip.amount }
            val expenses = monthTrips.sumOf { it.expenses.sumOf { e -> e.amount } }

            result += PeriodAnalytics(
                title = monthTitle(cursor),
                tripsCount = monthTrips.size,
                revenue = revenue,
                expenses = expenses,
                profit = TripFinanceCalculator.grossProfit(revenue, expenses)
            )

            cursor = cursor.plusMonths(1)
        }

        return result.asReversed()
    }

    private fun monthTitle(ym: YearMonth): String {
        val locale = Locale.forLanguageTag("ru")
        return ym.month.getDisplayName(TextStyle.FULL_STANDALONE, locale)
            .replaceFirstChar { it.uppercase() }
    }

    private fun buildYears(trips: List<TripWithExpenses>): List<PeriodAnalytics> {
        if (trips.isEmpty()) return emptyList()

        val zone = ZoneId.systemDefault()

        val grouped = trips.groupBy {
            it.trip.date.atZone(zone).year
        }

        val minYear = grouped.keys.min()
        val maxYear = grouped.keys.max()

        val result = mutableListOf<PeriodAnalytics>()

        for (year in minYear..maxYear) {
            val yearTrips = grouped[year].orEmpty()

            val revenue = yearTrips.sumOf { it.trip.amount }
            val expenses = yearTrips.sumOf { it.expenses.sumOf { e -> e.amount } }

            result += PeriodAnalytics(
                title = year.toString(),
                tripsCount = yearTrips.size,
                revenue = revenue,
                expenses = expenses,
                profit = TripFinanceCalculator.grossProfit(revenue, expenses)
            )
        }

        return result
    }
}