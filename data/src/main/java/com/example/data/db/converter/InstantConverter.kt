package com.example.data.db.converter

import androidx.room.TypeConverter
import java.time.Instant

class InstantConverter {

    @TypeConverter
    fun fromInstant(value: Instant): Long {
        return value.toEpochMilli()
    }

    @TypeConverter
    fun toInstant(value: Long): Instant {
        return Instant.ofEpochMilli(value)
    }
}