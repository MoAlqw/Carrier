package com.example.carrier.validation.company

import com.example.carrier.validation.ValidationError

sealed interface CompanyValidationError: ValidationError {

    data object NameEmpty : CompanyValidationError

    data object BinEmpty : CompanyValidationError
    data object BinInvalid : CompanyValidationError

    data object IicEmpty : CompanyValidationError
    data object IicInvalid : CompanyValidationError

    data object BankEmpty : CompanyValidationError

    data object BicEmpty : CompanyValidationError
    data object BicInvalid : CompanyValidationError

    data object PhoneEmpty : CompanyValidationError
    data object PhoneInvalid : CompanyValidationError

    data object EmailEmpty : CompanyValidationError
    data object EmailInvalid : CompanyValidationError

    data object AddressEmpty : CompanyValidationError
}