package com.example.domain.model

data class Company(
    val id: Int,
    val name: String,
    val binIin: String,
    val iic: String,
    val bank: String,
    val bic: String,
    val phone: String,
    val email: String,
    val address: String
)