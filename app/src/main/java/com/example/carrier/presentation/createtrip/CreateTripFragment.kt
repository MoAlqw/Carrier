package com.example.carrier.presentation.createtrip

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.carrier.R
import com.example.carrier.databinding.FragmentTripFormBinding
import com.example.carrier.extension.showError
import com.example.carrier.common.BaseFragment
import com.example.carrier.utils.DateFormatter
import com.example.carrier.validation.trip.TripValidationError
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateTripFragment: BaseFragment<FragmentTripFormBinding>(
    FragmentTripFormBinding::inflate
) {

    private val viewModel: CreateTripViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeState()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    setUi(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errors.collect {
                    setErrors(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tripCreated.collect {
                    findNavController().popBackStack()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.vehicles.collect { vehicles ->
                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        vehicles.map { it.brand + " " + it.model }
                    )
                    binding.actvVehicle.setAdapter(adapter)
                }
            }
        }
    }

    private fun setUi(state: CreateTripForm) {
        val estimatedFuelCost = state.estimatedFuelCost

        binding.etDate.setText(state.date)
        binding.tvEstimatedFuelCost.text = getString(
            R.string.full_price,
            estimatedFuelCost
        )
        binding.tvFuelOfUser.text = getString(
            R.string.full_cost,
            estimatedFuelCost
        )
        binding.tvRevenueOfUser.text = getString(
            R.string.full_price,
            state.amountValue
        )
        binding.tvTaxOfUser.text = getString(
            R.string.full_cost,
            state.taxCost
        )
        binding.tvNetProfit.text = getString(
            R.string.full_price,
            state.netProfit
        )
        binding.tvProfitability.text = getString(
            R.string.full_profitability,
            state.profitability
        )
    }

    private fun setErrors(errors: Set<TripValidationError>) {
        binding.tilRoute.showError(
            errors,
            TripValidationError.RouteEmpty,
            R.string.error_route_empty
        )
        binding.tilClient.showError(
            errors,
            TripValidationError.ClientEmpty,
            R.string.error_client_empty
        )
        binding.tilKm.showError(
            errors,
            TripValidationError.KmEmpty,
            R.string.error_km_empty
        )
        binding.tilAmount.showError(
            errors,
            TripValidationError.AmountEmpty,
            R.string.error_amount_empty
        )
        binding.tilDate.showError(
            errors,
            TripValidationError.DateEmpty,
            R.string.error_date_empty
        )
        binding.tilVehicle.showError(
            errors,
            TripValidationError.VehicleEmpty,
            R.string.error_vehicle_empty
        )
    }

    private fun setupListeners() = with(binding) {
        etRoute.doAfterTextChanged {
            viewModel.updateForm {
                copy(route = it.toString())
            }
        }
        etClient.doAfterTextChanged {
            viewModel.updateForm {
                copy(client = it.toString())
            }
        }
        etKm.doAfterTextChanged {
            viewModel.updateForm {
                copy(km = it.toString())
            }
        }
        etAmount.doAfterTextChanged {
            viewModel.updateForm {
                copy(amount = it.toString())
            }
        }
        etFuelPrice.doAfterTextChanged {
            viewModel.updateForm {
                copy(fuelPrice = it.toString())
            }
        }
        etFuelConsumption.doAfterTextChanged {
            viewModel.updateForm {
                copy(fuelConsumption = it.toString())
            }
        }
        btnStartTrip.setOnClickListener {
            viewModel.createTrip()
        }
        etDate.setOnClickListener {
            openDatePicker()
        }
        actvVehicle.setOnItemClickListener { _, _, position, _ ->
            val vehicle = viewModel.vehicles.value[position]
            viewModel.onVehicleSelected(vehicle.id)
            binding.etFuelConsumption.setText(vehicle.fuelConsumption.toString())
        }
    }

    private fun openDatePicker() {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.pick_a_date_of_trip))
            .build()

        picker.show(parentFragmentManager, "date_picker")

        picker.addOnPositiveButtonClickListener { millis ->
            viewModel.updateForm {
                copy(date = DateFormatter.format(millis))
            }
        }
    }
}