package com.example.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "trips",
    foreignKeys = [
        ForeignKey(
            entity = DriverEntity::class,
            parentColumns = ["id"],
            childColumns = ["driverId"]
        ),
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

    val number: String,
    val date: Long,
    val route: String,

    val driverId: Long,
    val vehicleId: Long,

    val client: String,
    val amount: Long,
    val km: Double,
    val status: String,
    val createdAt: Long
)