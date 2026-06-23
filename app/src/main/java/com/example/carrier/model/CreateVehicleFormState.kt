package com.example.carrier.model

data class CreateVehicleFormState(
    val brand: String = "",
    val model: String = "",
    val plate: String = "",
    val fuelConsumption: String = ""
) {
    fun isValid(): Boolean {
        return brand.isNotBlank() &&
                model.isNotBlank() &&
                plate.isNotBlank() &&
                fuelConsumption.toDoubleOrNull() != null
    }
}