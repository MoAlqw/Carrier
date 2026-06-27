package com.example.carrier.presentation.trip

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.carrier.R
import com.example.carrier.databinding.FragmentTripBinding
import com.example.carrier.common.BaseFragment
import com.example.carrier.common.NavKeys
import com.example.carrier.common.TripItemUi
import com.example.domain.model.TripStatus
import com.example.carrier.utils.DateFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TripDetailsFragment : BaseFragment<FragmentTripBinding>(
    FragmentTripBinding::inflate
) {

    private val viewModel: TripDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tripId = requireArguments().getLong(NavKeys.TRIP_ID)
        viewModel.loadTrip(tripId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    private fun setUi(trip: TripUiState) {
        when (trip) {
            is TripUiState.Loading -> {
                setLoadingUi()
            }
            is TripUiState.Content -> {
                setContentUi(trip.trip)
            }
        }
    }

    private fun setLoadingUi() {
        showLoadingUi(true)
    }

    private fun setContentUi(trip: TripItemUi) {
        showLoadingUi(false)
        setBaseInfoUi(trip)
        setClickListeners(trip)
        setWithTripStatusUi(trip)
    }

    private fun showLoadingUi(show: Boolean) {
        if (show) {
            binding.pbLoading.visibility = View.VISIBLE
            binding.layoutMainContent.visibility = View.GONE
        } else {
            binding.pbLoading.visibility = View.GONE
            binding.layoutMainContent.visibility = View.VISIBLE
        }
    }

    private fun setWithTripStatusUi(trip: TripItemUi) {
        when (trip.status) {
            TripStatus.IN_PROGRESS -> {
                binding.btnFinishTrip.visibility = View.VISIBLE
                binding.tvStatusBadge.text = getString(R.string.on_the_way)
                binding.tvStatusBadge.setBackgroundResource(R.drawable.bg_plate_light_green)
                binding.tvStatusBadge.setTextColor(
                    getColor(requireContext(),R.color.main_green)
                )
                binding.tvStatusBadge.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.cargo,
                    0,
                    0,
                    0
                )
            }
            TripStatus.CLOSED -> {
                binding.btnFinishTrip.visibility = View.GONE
                binding.tvStatusBadge.text = getString(R.string.close)
                binding.tvStatusBadge.setBackgroundResource(R.drawable.bg_plate_light_grey)
                binding.tvStatusBadge.setTextColor(
                    getColor(requireContext(), R.color.dark_grey)
                )
                binding.tvStatusBadge.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.check_arrow,
                    0,
                    0,
                    0
                )
            }
        }
    }

    private fun setClickListeners(trip: TripItemUi) {
        binding.btnOpenExpenses.setOnClickListener {
            val bundle = Bundle().apply {
                putLong(NavKeys.TRIP_ID, trip.id)
            }
            findNavController().navigate(
                R.id.action_tripDetailsFragment_to_tripExpensesFragment,
                bundle
            )
        }
        binding.btnFinishTrip.setOnClickListener {
            viewModel.updateTripStatus()
        }
    }

    private fun setBaseInfoUi(trip: TripItemUi) {
        binding.tvRoute.text = trip.route
        binding.tvDate.text = DateFormatter.format(trip.date.toEpochMilli())
        binding.tvClient.text = trip.client
        binding.tvVehicle.text = getString(
            R.string.vehicle_plate_string,
            trip.vehicle.brand,
            trip.vehicle.model,
            trip.vehicle.plate
        )
        binding.tvKm.text = getString(R.string.total_mileage, trip.km)
        binding.tvRevenue.text = getString(R.string.full_price, trip.amount)
        binding.tvExpensesTotal.text = getString(R.string.full_cost, trip.totalExpenses)
        binding.tvGrossProfit.text = getString(R.string.full_price, trip.grossProfit)
        binding.tvTax.text = getString(R.string.full_cost, trip.tax)
        binding.tvNetProfit.text = getString(R.string.full_price, trip.netProfit)
        binding.tvProfitability.text = getString(R.string.full_profitability, trip.profitability)
        binding.tvExpensesCount.text = getString(R.string.count_of_positions, trip.expenses.size)
    }
}