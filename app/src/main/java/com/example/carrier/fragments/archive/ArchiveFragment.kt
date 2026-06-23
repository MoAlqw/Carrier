package com.example.carrier.fragments.archive

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carrier.R
import com.example.carrier.databinding.FragmentArchiveBinding
import com.example.carrier.fragments.BaseFragment
import com.example.carrier.fragments.NavKeys
import com.example.carrier.fragments.flights.trips.adapter.TripsAdapter
import com.example.carrier.model.TripItemUi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchiveFragment : BaseFragment<FragmentArchiveBinding>(
    FragmentArchiveBinding::inflate
) {

    private val viewModel: ArchiveViewModel by viewModels()
    private val adapter by lazy {
        TripsAdapter { trip ->
            val bundle = Bundle().apply {
                putLong(NavKeys.TRIP_ID, trip.id)
            }

            findNavController().navigate(
                R.id.action_archiveFragment_to_tripDetailsFragment,
                bundle
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        observeState()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { trips ->
                    setUi(trips)
                }
            }
        }
    }

    private fun setUi(trips: List<TripItemUi>) {
        binding.tvTripsCount.text = trips.size.toString()
        binding.tvRevenueTotal.text = getString(
            R.string.full_price,
            trips.sumOf { it.amount }
        )
        binding.tvExpensesTotal.text = getString(
            R.string.full_cost,
            trips.sumOf {
                it.expenses.sumOf { expense ->  expense.amount }
            }
        )
        binding.tvProfitTotal.text = getString(
            R.string.full_price,
            trips.sumOf { it.amount } - trips.sumOf {
                it.expenses.sumOf { expense ->  expense.amount }
            }
        )
        if (trips.isEmpty()) {
            binding.rvArchive.visibility = View.GONE
            binding.layoutEmptyState.visibility = View.VISIBLE
        } else {
            binding.rvArchive.visibility = View.VISIBLE
            binding.layoutEmptyState.visibility = View.GONE
            adapter.submitList(trips)
        }
    }

    private fun setupRecycler() {
        binding.rvArchive.layoutManager = LinearLayoutManager(requireContext())
        binding.rvArchive.adapter = adapter
    }
}