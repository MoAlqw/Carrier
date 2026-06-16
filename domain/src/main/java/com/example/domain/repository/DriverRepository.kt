package com.example.domain.repository

import com.example.domain.model.Driver
import kotlinx.coroutines.flow.Flow

interface DriverRepository {
    suspend fun getDriver(): Flow<Driver?>
}