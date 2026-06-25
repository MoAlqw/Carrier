package com.example.carrier.model

data class CreateCompanyFormState(
    val name: String = "",
    val binIin: String = "",
    val iic: String = "",
    val bank: String = "",
    val bic: String = "",
    val phone: String = "",
    val email: String = "",
    val address: String = ""
) {

}