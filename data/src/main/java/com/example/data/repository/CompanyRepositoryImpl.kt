package com.example.data.repository

import com.example.data.db.dao.CompanyDao
import com.example.data.db.entity.toCompany
import com.example.domain.model.Company
import com.example.domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyDao: CompanyDao
) : CompanyRepository {
    override suspend fun getCompany(): Flow<Company?> {
        return companyDao.observeCompany().map { it?.toCompany() }
    }
}