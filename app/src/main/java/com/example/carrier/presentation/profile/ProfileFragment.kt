package com.example.carrier.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.carrier.R
import com.example.carrier.databinding.FragmentProfileBinding
import com.example.carrier.extension.showError
import com.example.carrier.common.BaseFragment
import com.example.carrier.validation.company.CompanyValidationError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {

    private val viewModel: ProfileViewModel by viewModels()
    private val vehicleAdapter = VehicleAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        observeErrors()
        setupListeners()
        setupRecyclerView()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    setUi(it)
                }
            }
        }
    }

    private fun observeErrors() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errors.collect {
                    setErrors(it)
                }
            }
        }
    }

    private fun setUi(item: ProfileUiState) {
        when (item) {
            is ProfileUiState.Loading -> {
                setLoadingUi()
            }

            is ProfileUiState.CompanyNotCreated -> {
                setCompanyNotCreatedUi()
            }

            is ProfileUiState.Content -> {
                setCompanyExistUi(item)
            }
        }
    }

    private fun setCompanyExistUi(item: ProfileUiState.Content) {
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
            vehicleAdapter.submitList(item.vehicle)
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

    private fun setCompanyNotCreatedUi() {
        binding.llCreateCompany.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.svCompanyInfo.visibility = View.GONE
    }

    private fun setLoadingUi() {
        binding.progressBar.visibility = View.VISIBLE
        binding.svCompanyInfo.visibility = View.GONE
        binding.llCreateCompany.visibility = View.GONE
    }

    private fun setupListeners() {
        setupFormListeners()

        binding.btnSave.setOnClickListener {
            viewModel.createCompany()
        }

        binding.btnAddVehicle.setOnClickListener {
            findNavController().navigate(R.id.addVehicleDialogFragment)
        }
    }

    private fun setupFormListeners() {
        binding.etName.doAfterTextChanged {
            viewModel.updateForm { copy(name = it.toString()) }
        }
        binding.etBinIin.doAfterTextChanged {
            viewModel.updateForm { copy(binIin = it.toString()) }
        }
        binding.etIic.doAfterTextChanged {
            viewModel.updateForm { copy(iic = it.toString()) }
        }
        binding.etBank.doAfterTextChanged {
            viewModel.updateForm { copy(bank = it.toString()) }
        }
        binding.etBic.doAfterTextChanged {
            viewModel.updateForm { copy(bic = it.toString()) }
        }
        binding.etPhone.doAfterTextChanged {
            viewModel.updateForm { copy(phone = it.toString()) }
        }
        binding.etEmail.doAfterTextChanged {
            viewModel.updateForm { copy(email = it.toString()) }
        }
        binding.etAddress.doAfterTextChanged {
            viewModel.updateForm { copy(address = it.toString()) }
        }
    }

    private fun setupRecyclerView() {
        binding.rvVehicles.adapter = vehicleAdapter
    }

    private fun setErrors(errors: Set<CompanyValidationError>) {
        binding.tilName.showError(
            errors,
            CompanyValidationError.NameEmpty,
            R.string.enter_name_of_company
        )

        binding.tilBinIin.showError(
            errors,
            CompanyValidationError.BinEmpty,
            R.string.enter_bin_iin,
            CompanyValidationError.BinInvalid,
            R.string.error_bin_iin_format
        )

        binding.tilIic.showError(
            errors,
            CompanyValidationError.IicEmpty,
            R.string.enter_iic,
            CompanyValidationError.IicInvalid,
            R.string.error_iic_format
        )

        binding.tilBank.showError(
            errors,
            CompanyValidationError.BankEmpty,
            R.string.enter_bank
        )

        binding.tilBic.showError(
            errors,
            CompanyValidationError.BicEmpty,
            R.string.enter_bic,
            CompanyValidationError.BicInvalid,
            R.string.error_bic_format
        )

        binding.tilPhone.showError(
            errors,
            CompanyValidationError.PhoneEmpty,
            R.string.enter_phone,
            CompanyValidationError.PhoneInvalid,
            R.string.error_phone_format
        )

        binding.tilEmail.showError(
            errors,
            CompanyValidationError.EmailEmpty,
            R.string.enter_email,
            CompanyValidationError.EmailInvalid,
            R.string.error_email_format
        )

        binding.tilAddress.showError(
            errors,
            CompanyValidationError.AddressEmpty,
            R.string.enter_address
        )
    }
}