package com.example.carrier.fragments.profile.addvehicle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrier.model.CreateVehicleFormState
import com.example.carrier.model.toVehicle
import com.example.carrier.validation.vehicle.VehicleValidationError
import com.example.carrier.validation.vehicle.VehicleValidator
import com.example.domain.usecase.CreateVehicleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddVehicleViewModel @Inject constructor(
    private val createVehicleUseCase: CreateVehicleUseCase,
    private val vehicleValidator: VehicleValidator
) : ViewModel() {

    private val _errors = MutableStateFlow<Set<VehicleValidationError>>(emptySet())
    val errors = _errors.asStateFlow()

    private val _createVehicleState = MutableStateFlow(CreateVehicleFormState())

    private val _vehicleCreated = MutableSharedFlow<Unit>()
    val vehicleCreated = _vehicleCreated.asSharedFlow()

    fun updateForm(update: CreateVehicleFormState.() -> CreateVehicleFormState) {
        _createVehicleState.update {
            it.update()
        }
    }

    fun createVehicle() {
        viewModelScope.launch {
            val state = _createVehicleState.value
            val errors = vehicleValidator.validate(state)
            _errors.value = errors
            if (errors.isNotEmpty()) return@launch
            createVehicleUseCase(state.toVehicle())
            _vehicleCreated.emit(Unit)
        }
    }
}