package com.example.carrier.presentation.addexpense

import android.app.Dialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.carrier.R
import com.example.carrier.databinding.FragmentAddExpenseDialogBinding
import com.example.carrier.extension.showError
import com.example.carrier.extension.toDisplayName
import com.example.carrier.common.NavKeys
import com.example.domain.model.ExpenseCategory
import com.example.carrier.utils.DateFormatter
import com.example.carrier.validation.expense.ExpenseValidationError
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddExpenseDialogFragment : DialogFragment() {

    private val viewModel: AddExpenseDialogViewModel by viewModels()

    private var _binding: FragmentAddExpenseDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentAddExpenseDialogBinding.inflate(layoutInflater)

        viewModel.initTripId(requireArguments().getLong(NavKeys.TRIP_ID))

        setupListeners()
        observeState()

        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
    }

    private fun observeState() {
        observeExpenseCreated()
        observeErrors()
    }

    private fun observeExpenseCreated() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.expenseCreated.collect {
                    dismiss()
                }
            }
        }
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

    private fun setErrors(errors: Set<ExpenseValidationError>) {
        binding.tilDate.showError(
            errors,
            ExpenseValidationError.DateEmpty,
            R.string.error_date_empty
        )
        binding.tilOther.showError(
            errors,
            ExpenseValidationError.NameEmpty,
            R.string.error_name_empty
        )
        binding.tilCategory.showError(
            errors,
            ExpenseValidationError.CategoryEmpty,
            R.string.error_category_empty
        )
        binding.tilAmount.showError(
            errors,
            ExpenseValidationError.SumEmpty,
            R.string.error_amount_empty,
            ExpenseValidationError.SumInvalid,
            R.string.error_amount_invalid
        )
    }

    private fun setupListeners() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnSave.setOnClickListener {
            viewModel.createExpense()
        }
        binding.etDate.setOnClickListener {
            openDatePicker()
        }
        binding.etAmount.doAfterTextChanged {
            viewModel.updateForm {
                copy(sum = it.toString())
            }
        }
        binding.etOther.doAfterTextChanged {
            viewModel.updateForm {
                copy(name = it.toString())
            }
        }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            ExpenseCategory.entries.map { it.toDisplayName(requireContext()) }
        )

        binding.actvCategory.setAdapter(adapter)

        binding.actvCategory.setOnItemClickListener { _, _, position, _ ->
            viewModel.updateForm {
                copy(category = ExpenseCategory.entries[position])
            }
        }
    }

    private fun openDatePicker() {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.pick_a_date_of_expense))
            .build()

        picker.show(parentFragmentManager, "date_picker")

        picker.addOnPositiveButtonClickListener { millis ->
            binding.etDate.setText(DateFormatter.format(millis))
            viewModel.updateForm {
                copy(date = millis.toString())
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}