package com.example.carrier.fragments.profile.addvehicle

import android.app.Dialog
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.carrier.R
import com.example.carrier.databinding.DialogVehicleFormBinding
import com.example.carrier.extension.showError
import com.example.carrier.validation.vehicle.VehicleValidationError
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddVehicleDialogFragment : DialogFragment() {

    private var _binding: DialogVehicleFormBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddVehicleViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogVehicleFormBinding.inflate(layoutInflater)

        setupListeners()
        observeErrors()
        observeVehicleCreated()

        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
    }

    private fun observeErrors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errors.collect {
                    setErrors(it)
                }
            }
        }
    }

    private fun observeVehicleCreated() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.vehicleCreated.collect {
                    dismiss()
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.etBrand.doAfterTextChanged {
            viewModel.updateForm {
                copy(brand = it.toString())
            }
        }
        binding.etModel.doAfterTextChanged {
            viewModel.updateForm {
                copy(model = it.toString())
            }
        }
        binding.etPlate.doAfterTextChanged {
            viewModel.updateForm {
                copy(plate = it.toString())
            }
        }
        binding.etFuelConsumption.doAfterTextChanged {
            viewModel.updateForm {
                copy(fuelConsumption = it.toString())
            }
        }
        binding.btnSave.setOnClickListener {
            viewModel.createVehicle()
        }
    }

    private fun setErrors(errors: Set<VehicleValidationError>) {
        binding.tilBrand.showError(
            errors,
            VehicleValidationError.BrandEmpty,
            R.string.enter_brand
        )
        binding.tilModel.showError(
            errors,
            VehicleValidationError.ModelEmpty,
            R.string.enter_model
        )
        binding.tilPlate.showError(
            errors,
            VehicleValidationError.PlateEmpty,
            R.string.enter_plate,
            VehicleValidationError.PlateInvalid,
            R.string.error_plate_format
        )
        binding.tilFuelConsumption.showError(
            errors,
            VehicleValidationError.FuelConsumptionEmpty,
            R.string.enter_fuel_consumption,
            VehicleValidationError.FuelConsumptionInvalid,
            R.string.error_fuel_consumption_format
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}