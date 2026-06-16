package com.example.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Driver

@Entity(tableName = "drivers")
data class DriverEntity(

    @PrimaryKey val id: Long = 1,

    val name: String,
)

fun DriverEntity.toDriver() = Driver(
    id = id,
    name = name
)