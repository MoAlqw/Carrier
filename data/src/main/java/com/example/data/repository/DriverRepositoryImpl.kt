package com.example.data.repository

import com.example.data.db.dao.DriverDao
import com.example.data.db.entity.toDriver
import com.example.domain.model.Driver
import com.example.domain.repository.DriverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DriverRepositoryImpl @Inject constructor(
    private val driverDao: DriverDao
): DriverRepository {
    override suspend fun getDriver(): Flow<Driver?> {
        return withContext(Dispatchers.IO) {
            val driver = driverDao.getDriver()
            driver.map { it?.toDriver() }
        }
    }
}