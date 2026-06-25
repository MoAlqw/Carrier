package com.example.carrier.fragments.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrier.model.CreateCompanyFormState
import com.example.carrier.model.ProfileUiState
import com.example.carrier.model.toVehicleUi
import com.example.domain.model.Company
import com.example.domain.usecase.CreateCompanyUseCase
import com.example.domain.usecase.GetCompanyUseCase
import com.example.domain.usecase.GetVehiclesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val _companyUiState = MutableStateFlow(CreateCompanyFormState())
    val companyUiState: StateFlow<CreateCompanyFormState> = _companyUiState

    val uiState: StateFlow<ProfileUiState> = combine(
        getCompanyUseCase(),
        getVehiclesUseCase()
    ) { company, vehicles ->
        if (company == null) {
            ProfileUiState.CompanyNotCreated
        } else {
            ProfileUiState.Content(
                company = company,
                vehicle = vehicles.map { it.toVehicleUi() }
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

    fun onNameChanged(name: String) {
        _companyUiState.value = _companyUiState.value.copy(name = name)
    }

    fun onBinIinChanged(binIin: String) {
        _companyUiState.value = _companyUiState.value.copy(binIin = binIin)
    }

    fun onIicChanged(iic: String) {
        _companyUiState.value = _companyUiState.value.copy(iic = iic)
    }

    fun onBankChanged(bank: String) {
        _companyUiState.value = _companyUiState.value.copy(bank = bank)
    }

    fun onBicChanged(bic: String) {
        _companyUiState.value = _companyUiState.value.copy(bic = bic)
    }

    fun onPhoneChanged(phone: String) {
        _companyUiState.value = _companyUiState.value.copy(phone = phone)
    }

    fun onEmailChanged(email: String) {
        _companyUiState.value = _companyUiState.value.copy(email = email)
    }

    fun onAddressChanged(address: String) {
        _companyUiState.value = _companyUiState.value.copy(address = address)
    }

    fun createCompany() = viewModelScope.launch {
        val company = _companyUiState.value
        createCompanyUseCase(
            Company(
                id = 0,
                name = company.name,
                binIin = company.binIin,
                iic = company.iic,
                bank = company.bank,
                bic = company.bic,
                phone = company.phone,
                email = company.email,
                address = company.address
            )
        )
    }
}