package com.example.carrier.presentation.analytics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carrier.R
import com.example.carrier.databinding.ItemPeriodDynamicsBinding
import com.example.domain.model.PeriodAnalytics

class PeriodAdapter:
    ListAdapter<PeriodAnalytics, PeriodAdapter.PeriodViewHolder>(PeriodDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeriodViewHolder {
        val binding = ItemPeriodDynamicsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PeriodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeriodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PeriodViewHolder(
        private val binding: ItemPeriodDynamicsBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(period: PeriodAnalytics) {
            binding.tvPeriodName.text = period.title
            binding.tvPeriodTripsCount.text = binding.root.context.getString(
                R.string.count_of_trips,
                period.tripsCount
            )
            binding.progressRevenue.progress = period.profitability.toInt()
            binding.tvPeriodRevenue.text = binding.root.context.getString(
                R.string.full_price,
                period.revenue
            )
            binding.tvPeriodExpenses.text = binding.root.context.getString(
                R.string.full_cost,
                period.expenses
            )
            binding.tvPeriodProfit.text = binding.root.context.getString(
                R.string.full_price,
                period.profit
            )
        }
    }
    companion object PeriodDiff : DiffUtil.ItemCallback<PeriodAnalytics>() {
        override fun areItemsTheSame(oldItem: PeriodAnalytics, newItem: PeriodAnalytics): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: PeriodAnalytics, newItem: PeriodAnalytics): Boolean {
            return oldItem == newItem
        }
    }
}