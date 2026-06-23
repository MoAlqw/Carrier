package com.example.domain.usecase

import com.example.domain.model.Company
import com.example.domain.repository.CompanyRepository
import com.example.domain.repository.VehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

class GetCompanyUseCase(
    private val companyRepository: CompanyRepository
) {
     operator fun invoke(): Flow<Company?> = companyRepository.getCompany()
}