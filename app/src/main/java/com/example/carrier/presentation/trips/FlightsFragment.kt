package com.example.carrier.presentation.trips

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carrier.R
import com.example.carrier.databinding.FragmentFlightsBinding
import com.example.carrier.common.BaseFragment
import com.example.carrier.common.NavKeys
import com.example.carrier.common.TripItemUi
import com.example.carrier.common.TripsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FlightsFragment : BaseFragment<FragmentFlightsBinding>(
    FragmentFlightsBinding::inflate
) {

    private val viewModel: FlightsViewModel by viewModels()
    private val adapter by lazy {
        TripsAdapter { trip ->
            val bundle = Bundle().apply {
                putLong(NavKeys.TRIP_ID, trip.id)
            }

            findNavController().navigate(
                R.id.action_flightsFragment_to_tripDetailsFragment,
                bundle
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabNewTrip.setOnClickListener {
            findNavController().navigate(R.id.action_flightsFragment_to_createTripFragment)
        }
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
        binding.tvInProgressCount.text = trips.size.toString()
        if (trips.isEmpty()) {
            binding.rvTrips.visibility = View.GONE
            binding.layoutEmptyState.visibility = View.VISIBLE
        }
        else {
            binding.rvTrips.visibility = View.VISIBLE
            binding.layoutEmptyState.visibility = View.GONE
            adapter.submitList(trips)
        }
    }

    private fun setupRecycler() {
        binding.rvTrips.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTrips.adapter = adapter
    }
}