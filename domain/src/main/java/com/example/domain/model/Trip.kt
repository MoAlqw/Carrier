package com.example.domain.model

import java.time.Instant

data class Trip(
    val id: Long,
    val number: String,
    val date: Instant,
    val route: String,

    val driverId: Long,
    val vehicleId: Long,

    val client: String,
    val amount: Long,
    val km: Double,
    val status: TripStatus,
    val createdAt: Instant
)