package com.example.carrier.presentation.tripexpenses

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carrier.R
import com.example.carrier.databinding.FragmentTripExpensesBinding
import com.example.carrier.common.BaseFragment
import com.example.carrier.common.NavKeys
import com.example.domain.model.TripStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentTripExpenses : BaseFragment<FragmentTripExpensesBinding>(
    FragmentTripExpensesBinding::inflate
) {
    private val viewModel: FragmentTripExpensesViewModel by viewModels()
    private val adapter = ExpensesTripAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initTrip(requireArguments().getLong(NavKeys.TRIP_ID))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeState()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.trip.collect { trip ->
                    trip?.let { setUi(it) }
                }
            }
        }
    }

    private fun setUi(expenses: ExpensesTripUi) {
        setBaseInfoUi(expenses)
        setExpensesListUi(expenses)
        setWithTripStatusUi(expenses)
        setClickListeners(expenses)
    }

    private fun setClickListeners(expenses: ExpensesTripUi) {
        binding.btnAddExpense.setOnClickListener {
            val bundle = Bundle().apply {
                putLong(NavKeys.TRIP_ID, expenses.id)
            }

            findNavController().navigate(
                R.id.addExpenseDialogFragment,
                bundle
            )
        }
    }

    private fun setWithTripStatusUi(expenses: ExpensesTripUi) {
        if (expenses.status == TripStatus.IN_PROGRESS) {
            binding.btnAddExpense.visibility = View.VISIBLE
        } else {
            binding.btnAddExpense.visibility = View.GONE
        }
    }

    private fun setExpensesListUi(expenses: ExpensesTripUi) {
        if (expenses.expenses.isEmpty()) {
            binding.layoutEmptyState.visibility = View.VISIBLE
            binding.rvExpenses.visibility = View.GONE
        } else {
            binding.layoutEmptyState.visibility = View.GONE
            binding.rvExpenses.visibility = View.VISIBLE
            adapter.submitList(expenses.expenses)
        }
    }

    private fun setBaseInfoUi(expenses: ExpensesTripUi) {
        binding.tvRevenue.text = getString(R.string.full_price, expenses.amount)
        binding.tvExpensesTotal.text = getString(R.string.full_cost, expenses.totalExpenses)
        binding.tvTax.text = getString(R.string.full_cost, expenses.tax)
        binding.tvNetProfit.text = getString(R.string.full_price, expenses.netProfit)
    }

    private fun setupRecyclerView() {
        binding.rvExpenses.layoutManager = LinearLayoutManager(requireContext())
        binding.rvExpenses.adapter = adapter
    }
}