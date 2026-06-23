package com.example.carrier.fragments.analytics

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carrier.R
import com.example.carrier.databinding.FragmentAnalyticsBinding
import com.example.carrier.fragments.BaseFragment
import com.example.carrier.fragments.analytics.adapter.CategoryAdapter
import com.example.carrier.fragments.analytics.adapter.PeriodAdapter
import com.example.carrier.model.analytics.AnalyticsUiState
import com.example.domain.model.AnalyticsPeriod
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnalyticsFragment : BaseFragment<FragmentAnalyticsBinding>(
    FragmentAnalyticsBinding::inflate
) {

    private val viewModel: AnalyticsViewModel by viewModels()
    private val categoryAdapter = CategoryAdapter()
    private val periodAdapter = PeriodAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        observeState()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnPeriodMonth.setOnClickListener {
            viewModel.changePeriod(AnalyticsPeriod.MONTH)
        }
        binding.btnPeriodYear.setOnClickListener {
            viewModel.changePeriod(AnalyticsPeriod.YEAR)
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    it?.let { setUi(it) }
                }
            }
        }
    }

    private fun setUi(state: AnalyticsUiState) {
        binding.tvTripsCount.text = state.summary.tripsCount.toString()
        binding.tvRevenueTotal.text = getString(R.string.full_price, state.summary.revenue)
        binding.tvNetProfitTotal.text = getString(R.string.full_price, state.summary.profit)
        binding.tvProfitability.text = getString(R.string.full_profitability, state.summary.profitability)
        binding.tvTaxBase.text = getString(R.string.full_price, state.summary.profit)
        binding.tvTaxAmount.text = getString(R.string.full_cost, state.tax)
        binding.tvTaxToPay.text = getString(R.string.full_price, state.tax)

        if (state.categories.isEmpty()) {
            binding.rvCategoryBreakdown.visibility = View.GONE
            binding.layoutEmptyState.visibility = View.VISIBLE
        } else {
            binding.rvCategoryBreakdown.visibility = View.VISIBLE
            binding.layoutEmptyState.visibility = View.GONE
            categoryAdapter.submitList(state.categories)
        }
        periodAdapter.submitList(state.periods)
    }

    private fun setupRecycler() {
        binding.rvCategoryBreakdown.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategoryBreakdown.adapter = categoryAdapter

        binding.rvDynamics.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDynamics.adapter = periodAdapter
    }
}