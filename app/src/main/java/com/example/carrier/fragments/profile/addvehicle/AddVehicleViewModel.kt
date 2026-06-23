package com.example.carrier.fragments.profile.addvehicle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrier.model.CreateVehicleFormState
import com.example.domain.model.Vehicle
import com.example.domain.usecase.CreateVehicleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddVehicleViewModel @Inject constructor(
    private val createVehicleUseCase: CreateVehicleUseCase
) : ViewModel() {

    private val _createVehicleState = MutableStateFlow(CreateVehicleFormState())
    private val _vehicleCreated = MutableSharedFlow<Unit>()
    val vehicleCreated = _vehicleCreated.asSharedFlow()

    fun onBrandChanged(value: String) {
        _createVehicleState.value = _createVehicleState.value.copy(brand = value)
    }

    fun onModelChanged(value: String) {
        _createVehicleState.value = _createVehicleState.value.copy(model = value)
    }

    fun onPlateChanged(value: String) {
        _createVehicleState.value = _createVehicleState.value.copy(plate = value)
    }

    fun onFuelConsumptionChanged(value: String) {
        _createVehicleState.value = _createVehicleState.value.copy(fuelConsumption = value)
    }

    fun createVehicle() {
        val state = _createVehicleState.value
        if (!state.isValid()) return

        viewModelScope.launch {
            createVehicleUseCase(
                Vehicle(
                    id = 0,
                    brand = state.brand,
                    model = state.model,
                    plate = state.plate,
                    fuelConsumption = state.fuelConsumption.toDouble()
                )
            )
            _vehicleCreated.emit(Unit)
        }
    }
}