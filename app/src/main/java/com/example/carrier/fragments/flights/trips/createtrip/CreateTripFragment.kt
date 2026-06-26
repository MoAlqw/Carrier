package com.example.carrier.fragments.flights.trips.createtrip

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
import com.example.carrier.fragments.BaseFragment
import com.example.carrier.model.CreateTripFormState
import com.example.carrier.utils.DateFormatter
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

    private fun setUi(state: CreateTripFormState) {
        val estimatedFuelCost = state.estimatedFuelCost

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

    private fun setupListeners() = with(binding) {
        etRoute.doAfterTextChanged {
            viewModel.onRouteChanged(it.toString())
        }
        etClient.doAfterTextChanged {
            viewModel.onClientChanged(it.toString())
        }
        etKm.doAfterTextChanged {
            viewModel.onKmChanged(it.toString())
        }
        etAmount.doAfterTextChanged {
            viewModel.onAmountChanged(it.toString())
        }
        etFuelPrice.doAfterTextChanged {
            viewModel.onFuelPriceChanged(it.toString())
        }
        etFuelConsumption.doAfterTextChanged {
            viewModel.onFuelConsumptionChanged(it.toString())
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
            binding.etDate.setText(DateFormatter.format(millis))
            viewModel.onDateSelected(DateFormatter.toInstant(millis))
        }
    }
}