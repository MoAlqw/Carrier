package com.example.carrier.model

import com.example.domain.model.Company

sealed interface ProfileUiState {

    data object Loading : ProfileUiState

    data object CompanyNotCreated : ProfileUiState

    data class Content(
        val company: Company,
        val vehicle: List<VehicleItemUi>
    ) : ProfileUiState
}