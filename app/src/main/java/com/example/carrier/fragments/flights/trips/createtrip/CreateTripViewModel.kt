package com.example.carrier.fragments.flights.trips.createtrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrier.model.CreateTripForm
import com.example.carrier.model.toTrip
import com.example.carrier.validation.trip.TripValidationError
import com.example.carrier.validation.trip.TripValidator
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
import javax.inject.Inject

@HiltViewModel
class CreateTripViewModel @Inject constructor(
    private val createTripUseCase: CreateTripUseCase,
    getVehiclesUseCase: GetVehiclesUseCase,
    private val tripValidator: TripValidator
) : ViewModel() {

    private val _state = MutableStateFlow(CreateTripForm())
    val state = _state.asStateFlow()

    private val _errors = MutableStateFlow<Set<TripValidationError>>(emptySet())
    val errors = _errors.asStateFlow()

    private val _tripCreated = MutableSharedFlow<Unit>()
    val tripCreated = _tripCreated.asSharedFlow()

    val vehicles = getVehiclesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun updateForm(update: CreateTripForm.() -> CreateTripForm) {
        _state.update {
            it.update()
        }
    }

    fun onVehicleSelected(id: Long) {
        val vehicle = vehicles.value.find { it.id == id } ?: return

        updateForm {
            copy(
                vehicleId = vehicle.id,
                fuelConsumption = vehicle.fuelConsumption.toString()
            )
        }
    }

    fun createTrip() {
        viewModelScope.launch {
            val state = _state.value
            val errors = tripValidator.validate(state)
            _errors.value = errors
            if (errors.isEmpty()) {
                createTripUseCase(state.toTrip())
                _tripCreated.emit(Unit)
            }
        }
    }
}