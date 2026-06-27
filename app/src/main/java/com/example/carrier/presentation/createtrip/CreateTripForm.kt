package com.example.carrier.presentation.createtrip

import com.example.carrier.utils.DateFormatter
import com.example.domain.model.Trip
import com.example.domain.model.TripFinanceCalculator
import com.example.domain.model.TripStatus
import java.time.Instant
import kotlin.math.roundToLong

data class CreateTripForm(
    val date: String = "",
    val route: String = "",
    val vehicleId: String = "",
    val client: String = "",
    val amount: String = "",
    val km: String = "",
    val fuelPrice: String = "",
    val fuelConsumption: String = ""
) {
    val amountValue: Long
        get() = amount.toLongOrNull() ?: 0

    val kmValue: Double
        get() = km.toDoubleOrNull() ?: 0.0

    val fuelPriceValue: Long
        get() = fuelPrice.toLongOrNull() ?: 0

    val fuelConsumptionValue: Double
        get() = fuelConsumption.toDoubleOrNull() ?: 0.0

    private val grossProfit: Long
        get() = TripFinanceCalculator.grossProfit(amountValue, fullEstimatedFuelCost)

    val estimatedFuelCost: Long
        get() = TripFinanceCalculator.fuelCostOnHundredKm(fuelConsumptionValue, fuelPriceValue)

    val fullEstimatedFuelCost: Long
        get() = TripFinanceCalculator.fuelCost(kmValue, fuelConsumptionValue, fuelPriceValue)

    val taxCost: Long
        get() = TripFinanceCalculator.tax(grossProfit)

    val netProfit: Long
        get() = TripFinanceCalculator.netProfit(grossProfit)

    val profitability: Long
        get() = TripFinanceCalculator.profitability(amountValue, netProfit)
}

fun CreateTripForm.toTrip(): Trip {
    return Trip(
        id = 0,
        date = DateFormatter.toInstant(date.toLong()),
        route = route,
        vehicleId = vehicleId.toLong(),
        client = client,
        amount = amountValue,
        km = kmValue.roundToLong(),
        status = TripStatus.IN_PROGRESS,
        createdAt = Instant.now()
    )
}