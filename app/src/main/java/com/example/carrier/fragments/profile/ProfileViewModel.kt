package com.example.carrier.fragments.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrier.model.ProfileUiState
import com.example.domain.model.Company
import com.example.domain.usecase.CreateCompanyUseCase
import com.example.domain.usecase.GetCompanyUseCase
import com.example.domain.usecase.GetVehiclesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getCompanyUseCase: GetCompanyUseCase,
    getVehiclesUseCase: GetVehiclesUseCase,
    private val createCompanyUseCase: CreateCompanyUseCase
) : ViewModel() {

    val uiState: StateFlow<ProfileUiState> = combine(
        getCompanyUseCase(),
        getVehiclesUseCase()
    ) { company, vehicles ->
        if (company == null) {
            ProfileUiState.CompanyNotCreated
        } else {
            ProfileUiState.Content(
                company = company,
                vehicle = vehicles
            )
        }
    }
        .onStart {
            emit(ProfileUiState.Loading)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProfileUiState.Loading
        )

    fun createCompany(
        name: String,
        binIin: String,
        iic: String,
        bank: String,
        bic: String,
        phone: String,
        email: String,
        address: String
    ) = viewModelScope.launch {
        createCompanyUseCase(
            Company(
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
        )
    }
}