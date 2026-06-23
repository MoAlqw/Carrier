package com.example.carrier.fragments.profile.addvehicle

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.carrier.databinding.DialogVehicleFormBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddVehicleDialogFragment : DialogFragment() {

    private var _binding: DialogVehicleFormBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddVehicleViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogVehicleFormBinding.inflate(layoutInflater)

        setupListeners()

        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
    }

    private fun setupListeners() {

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            val brand = binding.etBrand.text.toString()
            val model = binding.etModel.text.toString()
            val plate = binding.etPlate.text.toString()
            val fuel = binding.etFuelConsumption.text.toString()

            if (!validate(brand = brand, model = model, plate = plate, fuel = fuel)) return@setOnClickListener

            viewModel.createVehicle(
                brand = brand,
                model = model,
                plate = plate,
                fuelConsumption = fuel.toDouble()
            )

            dismiss()
        }
    }

    private fun validate(
        brand: String,
        model: String,
        plate: String,
        fuel: String
    ): Boolean {
        var valid = true
        if (brand.isBlank()) valid = false
        else if (model.isBlank()) valid = false
        else if (plate.isBlank()) valid = false
        else if (fuel.isBlank()) valid = false
        return valid
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}