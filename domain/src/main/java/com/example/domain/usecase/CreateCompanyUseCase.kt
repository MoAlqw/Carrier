package com.example.domain.usecase

import com.example.domain.model.Company
import com.example.domain.repository.CompanyRepository

class CreateCompanyUseCase(
    private val companyRepository: CompanyRepository
) {
    suspend operator fun invoke(company: Company) {
        companyRepository.createCompany(company)
    }
}