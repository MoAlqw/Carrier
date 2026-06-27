package com.example.carrier.presentation.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrier.common.TripItemUi
import com.example.carrier.common.toTripItemUi
import com.example.domain.usecase.GetClosedTripsWithExpensesAndVehicleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    getClosedTripsWithExpensesAndVehicleUseCase: GetClosedTripsWithExpensesAndVehicleUseCase
) : ViewModel() {

    val uiState: StateFlow<List<TripItemUi>> =
        getClosedTripsWithExpensesAndVehicleUseCase()
            .map {
                it.map { tripWithExpensesAndVehicle ->
                    tripWithExpensesAndVehicle.toTripItemUi()
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
}