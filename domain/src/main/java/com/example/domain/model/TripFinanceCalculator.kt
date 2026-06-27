package com.example.domain.model

import kotlin.math.roundToLong

object TripFinanceCalculator {

    private const val TAX_RATE = 0.12

    fun grossProfit(
        income: Long,
        expenses: Long
    ): Long {
        return income - expenses
    }

    fun tax(
        grossProfit: Long
    ): Long {
        if (grossProfit <= 0) return 0
        return (grossProfit * TAX_RATE).roundToLong()
    }

    fun netProfit(grossProfit: Long): Long {
        return grossProfit - tax(grossProfit)
    }
    fun profitability(
        income: Long,
        netProfit: Long
    ): Long {
        if (income == 0L) return 0
        return (netProfit * 100.0 / income).roundToLong()
    }

    fun fuelCost(
        km: Double,
        fuelConsumption: Double,
        fuelPrice: Long
    ): Long {
        return (km * fuelConsumption / 100 * fuelPrice).roundToLong()
    }

    fun fuelCostOnHundredKm(
        fuelConsumption: Double,
        fuelPrice: Long
    ): Long {
        return (fuelConsumption * fuelPrice).roundToLong()
    }
}