package com.example.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.domain.model.Trip
import com.example.domain.model.TripStatus
import java.time.Instant

@Entity(
    tableName = "trips",
    foreignKeys = [
        ForeignKey(
            entity = VehicleEntity::class,
            parentColumns = ["id"],
            childColumns = ["vehicleId"]
        )
    ]
)

data class TripEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val date: Instant,
    val route: String,

    val vehicleId: Long,

    val client: String,
    val amount: Long,
    val km: Long,
    val status: TripStatus,
    val createdAt: Instant
)

fun TripEntity.toTrip() = Trip(
    id = id,
    date = date,
    route = route,
    vehicleId = vehicleId,
    client = client,
    amount = amount,
    km = km,
    status = status,
    createdAt = createdAt
)

fun Trip.toEntity() = TripEntity(
    id = id,
    date = date,
    route = route,
    vehicleId = vehicleId,
    client = client,
    amount = amount,
    km = km,
    status = status,
    createdAt = createdAt
)