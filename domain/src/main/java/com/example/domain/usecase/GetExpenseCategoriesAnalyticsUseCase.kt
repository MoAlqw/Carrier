package com.example.domain.usecase

import com.example.domain.model.CategoryAnalytics
import com.example.domain.model.TripWithExpenses
import kotlin.collections.flatMap

class GetExpenseCategoriesAnalyticsUseCase {
    operator fun invoke(trips: List<TripWithExpenses>): List<CategoryAnalytics> {

        val allExpenses = trips.flatMap { it.expenses }
        val total = allExpenses.sumOf { it.amount }

        return allExpenses
            .groupBy { it.category }
            .map { (category, list) ->

                val sum = list.sumOf { it.amount }

                CategoryAnalytics(
                    category = category,
                    amount = sum,
                    percent =
                        if (total == 0L) 0
                        else sum * 100 / total
                )
            }
    }
}