package com.example.data.db.converter

import androidx.room.TypeConverter
import com.example.domain.model.TripStatus

class TripStatusConverter {

    @TypeConverter
    fun fromStatus(status: TripStatus): String {
        return status.name
    }

    @TypeConverter
    fun toStatus(value: String): TripStatus {
        return TripStatus.valueOf(value)
    }
}