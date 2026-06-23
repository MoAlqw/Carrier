package com.example.domain.model

import java.time.Instant

data class Trip(
    val id: Long,
    val date: Instant,
    val route: String,

    val vehicleId: Long,

    val client: String,
    val amount: Long,
    val km: Long,
    val status: TripStatus,
    val createdAt: Instant
)