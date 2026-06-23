package com.example.data.repository

import com.example.data.db.dao.CompanyDao
import com.example.data.db.entity.toCompany
import com.example.data.db.entity.toCompanyEntity
import com.example.domain.model.Company
import com.example.domain.repository.CompanyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyDao: CompanyDao
) : CompanyRepository {

    override fun getCompany(): Flow<Company?> = companyDao.observeCompany().map {
        it?.toCompany()
    }

    override suspend fun createCompany(company: Company) {
        withContext(Dispatchers.IO) {
            companyDao.insert(company.toCompanyEntity())
        }
    }
}