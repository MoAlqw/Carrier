package com.example.domain.repository

import com.example.domain.model.Company
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {
    fun getCompany(): Flow<Company?>
    suspend fun createCompany(company: Company)
}