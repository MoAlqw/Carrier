package com.example.carrier.presentation.profile

import com.example.domain.model.Company

data class CreateCompanyForm(
    val name: String = "",
    val binIin: String = "",
    val iic: String = "",
    val bank: String = "",
    val bic: String = "",
    val phone: String = "",
    val email: String = "",
    val address: String = ""
)

fun CreateCompanyForm.toCompany(): Company {
    return Company(
        id = 0,
        name = name,
        binIin = binIin,
        iic = iic,
        bank = bank,
        bic = bic,
        phone = phone,
        email = email,
        address = address
    )
}