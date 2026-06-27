package com.example.carrier.validation.company

import com.example.carrier.presentation.profile.CreateCompanyForm
import javax.inject.Inject

class CompanyValidator @Inject constructor() {

    fun validate(company: CreateCompanyForm): Set<CompanyValidationError> {
        val errors = mutableSetOf<CompanyValidationError>()
        if (company.name.isBlank()) {
            errors.add(CompanyValidationError.NameEmpty)
        }

        if (company.binIin.isBlank()) {
            errors.add(CompanyValidationError.BinEmpty)
        } else if (!company.binIin.matches(BIN_REGEX)) {
            errors.add(CompanyValidationError.BinInvalid)
        }

        if (company.iic.isBlank()) {
            errors.add(CompanyValidationError.IicEmpty)
        } else if (!company.iic.matches(IIC_REGEX)) {
            errors.add(CompanyValidationError.IicInvalid)
        }

        if (company.bank.isBlank()) {
            errors.add(CompanyValidationError.BankEmpty)
        }

        if (company.bic.isBlank()) {
            errors.add(CompanyValidationError.BicEmpty)
        } else if (!company.bic.matches(BIC_REGEX)) {
            errors.add(CompanyValidationError.BicInvalid)
        }

        if (company.phone.isBlank()) {
            errors.add(CompanyValidationError.PhoneEmpty)
        } else if (!company.phone.matches(PHONE_REGEX)) {
            errors.add(CompanyValidationError.PhoneInvalid)
        }

        if (company.email.isBlank()) {
            errors.add(CompanyValidationError.EmailEmpty)
        } else if (!company.email.matches(EMAIL_REGEX)) {
            errors.add(CompanyValidationError.EmailInvalid)
        }

        if (company.address.isBlank()) {
            errors.add(CompanyValidationError.AddressEmpty)
        }

        return errors
    }

    private companion object {
        val BIN_REGEX = Regex("\\d{12}")
        val IIC_REGEX = Regex("[A-Z0-9]{18}")
        val BIC_REGEX = Regex("[A-Z0-9]{8}")
        val PHONE_REGEX = Regex("\\+?[\\d\\s\\-()]{7,15}")
        val EMAIL_REGEX = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
    }
}