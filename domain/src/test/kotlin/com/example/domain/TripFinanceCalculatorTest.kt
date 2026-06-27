package com.example.domain

import com.example.domain.model.TripFinanceCalculator
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TripFinanceCalculatorTest {

    @Test
    fun `grossProfit should return income minus expenses`() {
        val income = 100_000L
        val expenses = 50_000L

        val result = TripFinanceCalculator.grossProfit(
            income = income,
            expenses = expenses
        )

        assertThat(result).isEqualTo(50_000L)
    }

    @Test
    fun `grossProfit should return a negative value if expenses are more than income`() {
        val income = 50_000L
        val expenses = 100_000L

        val result = TripFinanceCalculator.grossProfit(
            income = income,
            expenses = expenses
        )

        assertThat(result).isEqualTo(50_000L - 100_000L)
    }

    @Test
    fun `tax should return 12 percent of grossProfit`() {
        val grossProfit = 50_000L

        val result = TripFinanceCalculator.tax(grossProfit)

        assertThat(result).isEqualTo(6_000L)
    }

    @Test
    fun `tax should return 0 if grossProfit is negative`() {
        val grossProfit = -50_000L

        val result = TripFinanceCalculator.tax(grossProfit)

        assertThat(result).isEqualTo(0L)
    }

    @Test
    fun `netProfit should return grossProfit minus tax`() {
        val grossProfit = 50_000L

        val result = TripFinanceCalculator.netProfit(grossProfit)

        assertThat(result).isEqualTo(44_000L)
    }

    @Test
    fun `netProfit should return negative value if grossProfit is negative`() {
        val grossProfit = -50_000L

        val result = TripFinanceCalculator.netProfit(grossProfit)

        assertThat(result).isEqualTo(-50_000L)
    }

    @Test
    fun `profitability should return netProfit divided by income`() {
        val income = 100_000L
        val netProfit = 44_000L

        val result = TripFinanceCalculator.profitability(income, netProfit)

        assertThat(result).isEqualTo(44L)
    }

    @Test
    fun `profitability should return 0 if income is 0`() {
        val income = 0L
        val netProfit = 0L

        val result = TripFinanceCalculator.profitability(income, netProfit)

        assertThat(result).isEqualTo(0L)
    }

    @Test
    fun `profitability should return 0 if netProfit is 0`() {
        val income = 100_000L
        val netProfit = 0L

        val result = TripFinanceCalculator.profitability(income, netProfit)

        assertThat(result).isEqualTo(0L)
    }

    @Test
    fun `fuelCost should return km times fuelConsumption divided by 100 times fuelPrice`() {
        val km = 100.0
        val fuelConsumption = 10.0
        val fuelPrice = 100L

        val result = TripFinanceCalculator.fuelCost(km, fuelConsumption, fuelPrice)

        assertThat(result).isEqualTo(1_000L)
    }

    @Test
    fun `fuelCostOnHundredKm should return fuelConsumption times fuelPrice`() {
        val fuelConsumption = 10.0
        val fuelPrice = 100L

        val result = TripFinanceCalculator.fuelCostOnHundredKm(fuelConsumption, fuelPrice)

        assertThat(result).isEqualTo(1000L)
    }
}