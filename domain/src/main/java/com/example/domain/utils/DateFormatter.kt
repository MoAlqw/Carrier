package com.example.domain.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateFormatter {
    private val formatter = DateTimeFormatter.ofPattern("d MMM yyyy")

    fun format(millis: Long): String {
        return Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .format(formatter)
    }

    fun toInstant(millis: Long): Instant {
        return Instant.ofEpochMilli(millis)
    }
}