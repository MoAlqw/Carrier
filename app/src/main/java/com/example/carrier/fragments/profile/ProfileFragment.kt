package com.example.carrier.fragments.profile

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.carrier.R
import com.example.carrier.databinding.FragmentProfileBinding
import com.example.carrier.fragments.BaseFragment
import com.example.carrier.model.ProfileUiState
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {

    private val viewModel: ProfileViewModel by viewModels()
    private val adapter = VehicleAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    setUi(it)
                }
            }
        }
        binding.btnSave.setOnClickListener {
            createCompany()
        }

        binding.btnAddVehicle.setOnClickListener {
            findNavController().navigate(R.id.addVehicleDialogFragment)
        }
    }

    private fun setUi(item: ProfileUiState) {
        when (item) {
            is ProfileUiState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.svCompanyInfo.visibility = View.GONE
                binding.llCreateCompany.visibility = View.GONE
            }

            is ProfileUiState.CompanyNotCreated -> {
                binding.llCreateCompany.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.svCompanyInfo.visibility = View.GONE
            }

            is ProfileUiState.Content -> {
                binding.svCompanyInfo.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.llCreateCompany.visibility = View.GONE

                if (item.vehicle.isEmpty()) {
                    binding.tvNoVehicles.visibility = View.VISIBLE
                    binding.btnAddVehicle.visibility = View.VISIBLE
                    binding.rvVehicles.visibility = View.GONE
                } else {
                    binding.rvVehicles.visibility = View.VISIBLE
                    binding.tvNoVehicles.visibility = View.GONE
                    binding.btnAddVehicle.visibility = if (item.vehicle.size < 3) View.VISIBLE else View.GONE
                    setupRecyclerView(item)
                }

                binding.tvNameCompany.text = item.company.name
                binding.tvIin.text = item.company.binIin
                binding.tvIICOfUser.text = item.company.iic
                binding.tvBancOfUser.text = item.company.bank
                binding.tvBICOfUser.text = item.company.bic
                binding.tvPhoneOfUser.text = item.company.phone
                binding.tvEmailOfUser.text = item.company.email
                binding.tvAddressOfUser.text = item.company.address
            }
        }
    }

    private fun setupRecyclerView(item: ProfileUiState.Content) {
        binding.rvVehicles.adapter = adapter
        adapter.submitList(item.vehicle)
    }

    private fun createCompany() {
        if (validateForm()) {
            viewModel.createCompany(
                name = binding.etName.text.toString(),
                binIin = binding.etBinIin.text.toString(),
                iic = binding.etIic.text.toString(),
                bank = binding.etBank.text.toString(),
                bic = binding.etBic.text.toString(),
                phone = binding.etPhone.text.toString(),
                email = binding.etEmail.text.toString(),
                address = binding.etAddress.text.toString(),
            )
        }
    }

    private fun validateForm(): Boolean {
        var valid = true

        fun checkBlank(til: TextInputLayout, et: TextInputEditText, errorRes: Int) {
            if (et.text.isNullOrBlank()) {
                til.error = getString(errorRes)
                valid = false
            } else {
                til.error = null
            }
        }

        fun checkPattern(
            til: TextInputLayout,
            et: TextInputEditText,
            pattern: Regex,
            blankRes: Int,
            formatRes: Int
        ) {
            val text = et.text.toString()
            when {
                text.isBlank() -> {
                    til.error = getString(blankRes); valid = false
                }

                !text.matches(pattern) -> {
                    til.error = getString(formatRes); valid = false
                }

                else -> til.error = null
            }
        }

        checkBlank(binding.tilName, binding.etName, R.string.enter_name_of_company)
        checkPattern(
            binding.tilBinIin,
            binding.etBinIin,
            Regex("\\d{12}"),
            R.string.enter_bin_iin,
            R.string.error_bin_iin_format
        )
        checkPattern(
            binding.tilIic,
            binding.etIic,
            Regex("[A-Z0-9]{18}"),
            R.string.enter_iic,
            R.string.error_iic_format
        )
        checkBlank(binding.tilBank, binding.etBank, R.string.enter_bank)
        checkPattern(
            binding.tilBic,
            binding.etBic,
            Regex("[A-Z0-9]{8}"),
            R.string.enter_bic,
            R.string.error_bic_format
        )
        checkPattern(
            binding.tilPhone,
            binding.etPhone,
            Regex("\\+?[\\d\\s\\-()]{7,15}"),
            R.string.enter_phone,
            R.string.error_phone_format
        )
        checkPattern(
            binding.tilEmail,
            binding.etEmail,
            Patterns.EMAIL_ADDRESS.toRegex(),
            R.string.enter_email,
            R.string.error_email_format
        )
        checkBlank(binding.tilAddress, binding.etAddress, R.string.enter_address)

        return valid
    }
}