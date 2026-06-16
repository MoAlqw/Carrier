package com.example.domain.repository

import com.example.domain.model.Company
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {
    suspend fun getCompany(): Flow<Company?>
}