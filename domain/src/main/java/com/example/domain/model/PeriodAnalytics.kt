package com.example.domain.model

data class PeriodAnalytics(
    val title: String,
    val tripsCount: Int,
    val revenue: Long,
    val expenses: Long,
    val profit: Long
) {
    val profitability: Long
        get() = if (profit == 0L) 0 else revenue * 100 / profit
}