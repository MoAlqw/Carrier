package com.example.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Company

@Entity(tableName = "company")
data class CompanyEntity(

    @PrimaryKey val id: Int = 0,

    val name: String,
    val binIin: String,
    val iik: String,
    val bank: String,
    val bik: String,
    val phone: String,
    val email: String,
    val address: String
)

fun CompanyEntity.toCompany() = Company(
    id = id,
    name = name,
    binIin = binIin,
    iic = iik,
    bank = bank,
    bic = bik,
    phone = phone,
    email = email,
    address = address
)

fun Company.toCompanyEntity() = CompanyEntity(
    id = id,
    name = name,
    binIin = binIin,
    iik = iic,
    bank = bank,
    bik = bic,
    phone = phone,
    email = email,
    address = address
)