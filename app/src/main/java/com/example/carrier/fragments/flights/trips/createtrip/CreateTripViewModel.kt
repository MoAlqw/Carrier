package com.example.carrier.fragments.flights.trips.createtrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrier.model.CreateTripFormState
import com.example.domain.model.Trip
import com.example.domain.model.TripStatus
import com.example.domain.usecase.CreateTripUseCase
import com.example.domain.usecase.GetVehiclesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class CreateTripViewModel @Inject constructor(
    private val createTripUseCase: CreateTripUseCase,
    getVehiclesUseCase: GetVehiclesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CreateTripFormState())
    val state = _state.asStateFlow()

    private val _tripCreated = MutableSharedFlow<Unit>()
    val tripCreated = _tripCreated.asSharedFlow()

    val vehicles = getVehiclesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onDateSelected(date: Instant) {
        _state.update { it.copy(date = date) }
    }

    fun onRouteChanged(value: String) {
        _state.update { it.copy(route = value) }
    }

    fun onClientChanged(value: String) {
        _state.update { it.copy(client = value) }
    }

    fun onVehicleSelected(id: Long) {
        val vehicleSelected = vehicles.value.find { it.id == id } ?: return
        _state.update {
            it.copy(
                vehicleId = vehicleSelected.id,
                fuelConsumption = vehicleSelected.fuelConsumption.toString()
            )
        }
    }

    fun onAmountChanged(value: String) {
        _state.update { it.copy(amount = value) }
    }

    fun onKmChanged(value: String) {
        _state.update { it.copy(km = value) }
    }

    fun onFuelPriceChanged(value: String) {
        _state.update { it.copy(fuelPrice = value) }
    }

    fun onFuelConsumptionChanged(value: String) {
        _state.update { it.copy(fuelConsumption = value) }
    }

    fun createTrip() {
        val state = _state.value

        if (!state.isValid()) return

        viewModelScope.launch {
            createTripUseCase(
                Trip(
                    id = 0,
                    date = state.date!!,
                    route = state.route,
                    vehicleId = state.vehicleId!!,
                    client = state.client,
                    amount = state.amount.toLong(),
                    km = state.km.toLong(),
                    status = TripStatus.IN_PROGRESS,
                    createdAt = Instant.now()
                )
            )
            _tripCreated.emit(Unit)
        }
    }
}