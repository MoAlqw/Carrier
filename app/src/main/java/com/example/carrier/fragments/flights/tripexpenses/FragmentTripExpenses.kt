package com.example.carrier.fragments.flights.tripexpenses

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
import com.example.carrier.fragments.BaseFragment
import com.example.carrier.fragments.NavKeys
import com.example.carrier.fragments.flights.tripexpenses.adapter.ExpensesTripAdapter
import com.example.carrier.model.ExpensesTripUi
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

    private fun setUi(trip: ExpensesTripUi) {
        binding.tvRevenue.text = getString(R.string.full_price, trip.amount)
        binding.tvExpensesTotal.text = getString(R.string.full_cost, trip.totalExpenses)
        binding.tvTax.text = getString(R.string.full_cost, trip.tax)
        binding.tvNetProfit.text = getString(R.string.full_price, trip.netProfit)
        if (trip.expenses.isEmpty()) {
            binding.layoutEmptyState.visibility = View.VISIBLE
            binding.rvExpenses.visibility = View.GONE
        } else {
            binding.layoutEmptyState.visibility = View.GONE
            binding.rvExpenses.visibility = View.VISIBLE
            adapter.submitList(trip.expenses)
        }
        if (trip.status == TripStatus.IN_PROGRESS) {
            binding.btnAddExpense.visibility = View.VISIBLE
            binding.btnAddExpense.setOnClickListener {
                val bundle = Bundle().apply {
                    putLong(NavKeys.TRIP_ID, trip.id)
                }

                findNavController().navigate(
                    R.id.addExpenseDialogFragment,
                    bundle
                )
            }
        } else {
            binding.btnAddExpense.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        binding.rvExpenses.layoutManager = LinearLayoutManager(requireContext())
        binding.rvExpenses.adapter = adapter
    }
}