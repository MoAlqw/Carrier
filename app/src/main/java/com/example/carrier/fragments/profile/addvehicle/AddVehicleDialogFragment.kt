package com.example.carrier.fragments.profile.addvehicle

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.carrier.databinding.DialogVehicleFormBinding
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
        observeVehicleCreated()

        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
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
        binding.etBrand.setOnClickListener {
            viewModel.onBrandChanged(it.toString())
        }
        binding.etModel.setOnClickListener {
            viewModel.onModelChanged(it.toString())
        }
        binding.etPlate.setOnClickListener {
            viewModel.onPlateChanged(it.toString())
        }
        binding.etFuelConsumption.setOnClickListener {
            viewModel.onFuelConsumptionChanged(it.toString())
        }
        binding.btnSave.setOnClickListener {
            viewModel.createVehicle()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}