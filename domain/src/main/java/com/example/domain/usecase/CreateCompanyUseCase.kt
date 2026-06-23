package com.example.domain.usecase

import com.example.domain.model.Company
import com.example.domain.repository.CompanyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateCompanyUseCase(
    private val companyRepository: CompanyRepository
) {
    suspend operator fun invoke(company: Company) {
        withContext(Dispatchers.IO) {
            companyRepository.createCompany(company)
        }
    }
}