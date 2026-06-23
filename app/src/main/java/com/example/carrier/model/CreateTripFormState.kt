package com.example.carrier.model

import java.time.Instant
import kotlin.math.roundToLong

data class CreateTripFormState(
    val date: Instant? = null,
    val route: String = "",
    val vehicleId: Long? = null,
    val client: String = "",
    val amount: String = "",
    val km: String = "",
    val fuelPrice: String = "",
    val fuelConsumption: String = ""
) {
    val amountValue: Long
        get() = amount.toLongOrNull() ?: 0

    val kmValue: Double?
        get() = km.toDoubleOrNull()

    val fuelPriceValue: Long?
        get() = fuelPrice.toLongOrNull()

    val fuelConsumptionValue: Double?
        get() = fuelConsumption.toDoubleOrNull()

    val estimatedFuelCost: Long
        get() {
            val km = kmValue ?: 0.0
            val consumption = fuelConsumptionValue ?: 0.0
            val price = fuelPriceValue ?: return 0

            return (km * consumption / 100 * price).roundToLong()
        }
    val taxCost: Long
        get() {
            return if (amountValue - estimatedFuelCost < 0) 0 else ((amountValue - estimatedFuelCost) * 0.12).roundToLong()
        }

    val netProfit: Long
        get() {
            return amountValue - taxCost - estimatedFuelCost
        }

    val profitability: Long
        get() =
            if (amountValue == 0L) 0
            else (netProfit * 100.0 / amountValue).roundToLong()

    fun isValid(): Boolean {
        return date != null &&
                route.isNotBlank() &&
                vehicleId != null &&
                client.isNotBlank() &&
                amount.toLongOrNull() != null &&
                km.toDoubleOrNull() != null
    }
}