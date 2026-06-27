package com.example.carrier.presentation.trip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrier.common.toTripItemUi
import com.example.domain.usecase.GetTripWithExpensesAndVehicleByIdUseCase
import com.example.domain.usecase.UpdateStatusTripUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripDetailsViewModel @Inject constructor(
    private val getTripWithExpensesAndVehicleByIdUseCase: GetTripWithExpensesAndVehicleByIdUseCase,
    private val updateStatusTripUseCase: UpdateStatusTripUseCase
) : ViewModel() {

    private val tripId = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val trip: StateFlow<TripUiState> =
        tripId
            .filterNotNull()
            .flatMapLatest { id ->
                getTripWithExpensesAndVehicleByIdUseCase(id)
                    .map {
                        TripUiState.Content(
                            it.toTripItemUi()
                        )
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = TripUiState.Loading
            )

    fun loadTrip(id: Long) {
        tripId.value = id
    }

    fun updateTripStatus() {
        val trip = trip.value as? TripUiState.Content ?: return
        viewModelScope.launch {
            updateStatusTripUseCase(trip.trip.id)
        }
    }
}