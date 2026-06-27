package com.example.carrier.presentation.trip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrier.common.TripItemUi
import com.example.carrier.common.toTripItemUi
import com.example.domain.usecase.GetTripsWithExpensesAndVehicleUseCase
import com.example.domain.usecase.UpdateStatusTripUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val getTripsWithExpensesAndVehicleUseCase: GetTripsWithExpensesAndVehicleUseCase,
    private val updateStatusTripUseCase: UpdateStatusTripUseCase
) : ViewModel() {

    private val tripId = MutableStateFlow<Long?>(null)

    val trip: StateFlow<TripItemUi?> =
        tripId
            .filterNotNull()
            .flatMapLatest { id ->
                getTripsWithExpensesAndVehicleUseCase()
                    .map {
                        it.first { tripWithExpensesAndVehicle ->
                            tripWithExpensesAndVehicle.trip.id == id
                        }.toTripItemUi()
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )

    fun loadTrip(id: Long) {
        tripId.value = id
    }

    fun updateTripStatus() {
        val trip = trip.value ?: return
        viewModelScope.launch {
            updateStatusTripUseCase(trip.id)
        }
    }
}