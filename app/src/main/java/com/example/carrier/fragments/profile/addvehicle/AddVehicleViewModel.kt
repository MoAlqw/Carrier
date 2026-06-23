package com.example.carrier.fragments.profile.addvehicle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Vehicle
import com.example.domain.usecase.CreateVehicleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddVehicleViewModel @Inject constructor(
    private val createVehicleUseCase: CreateVehicleUseCase
) : ViewModel() {

    fun createVehicle(
        brand: String,
        model: String,
        plate: String,
        fuelConsumption: Double
    ) = viewModelScope.launch {

        createVehicleUseCase(
            Vehicle(
                id = 0,
                brand = brand,
                model = model,
                plate = plate,
                fuelConsumption = fuelConsumption
            )
        )
    }
}