package com.example.carrier.presentation.trip

import com.example.carrier.common.TripItemUi

sealed interface TripUiState {

    data object Loading: TripUiState

    data class Content(val trip: TripItemUi): TripUiState
}