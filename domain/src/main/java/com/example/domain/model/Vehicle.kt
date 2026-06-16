package com.example.domain.model

data class Vehicle(
    val id: Long,
    val brand: String,
    val model: String,
    val plate: String,
    val fuelConsumption: Double
)