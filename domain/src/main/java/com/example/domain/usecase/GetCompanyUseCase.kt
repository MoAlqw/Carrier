package com.example.domain.usecase

import com.example.domain.model.Company
import com.example.domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow

class GetCompanyUseCase(
    private val companyRepository: CompanyRepository
) {
    operator fun invoke(): Flow<Company?> = companyRepository.getCompany()
}