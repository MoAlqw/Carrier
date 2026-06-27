package com.example.carrier.presentation.profile

import com.example.carrier.common.VehicleItemUi
import com.example.domain.model.Company

sealed interface ProfileUiState {

    data object Loading : ProfileUiState

    data object CompanyNotCreated : ProfileUiState

    data class Content(
        val company: Company,
        val vehicle: List<VehicleItemUi>
    ) : ProfileUiState
}