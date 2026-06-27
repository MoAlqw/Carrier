package com.example.carrier.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrier.common.toVehicleUi
import com.example.carrier.validation.company.CompanyValidationError
import com.example.carrier.validation.company.CompanyValidator
import com.example.domain.usecase.CreateCompanyUseCase
import com.example.domain.usecase.GetCompanyUseCase
import com.example.domain.usecase.GetVehiclesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getCompanyUseCase: GetCompanyUseCase,
    getVehiclesUseCase: GetVehiclesUseCase,
    private val createCompanyUseCase: CreateCompanyUseCase,
    private val validator: CompanyValidator
) : ViewModel() {

    private val _createCompanyForm = MutableStateFlow(CreateCompanyForm())

    private val _errors = MutableStateFlow<Set<CompanyValidationError>>(emptySet())
    val errors = _errors.asStateFlow()

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

    fun updateForm(update: CreateCompanyForm.() -> CreateCompanyForm) {
        _createCompanyForm.update {
            it.update()
        }
    }

    fun createCompany() = viewModelScope.launch {
        val company = _createCompanyForm.value
        val errors = validator.validate(company)
        _errors.value = errors
        if (errors.isEmpty()) createCompanyUseCase(company.toCompany())
    }
}