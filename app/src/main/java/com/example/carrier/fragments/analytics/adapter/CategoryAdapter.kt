package com.example.carrier.fragments.analytics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carrier.R
import com.example.carrier.databinding.ItemCategoryBreakdownBinding
import com.example.carrier.extension.toDisplayName
import com.example.domain.model.CategoryAnalytics

class CategoryAdapter:
    ListAdapter<CategoryAnalytics, CategoryAdapter.CategoryViewHolder>(CategoryDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBreakdownBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CategoryViewHolder(
        private val binding: ItemCategoryBreakdownBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryAnalytics) {
            binding.tvCategoryName.text = category.category.toDisplayName(binding.root.context)
            binding.tvCategoryAmount.text = binding.root.context.getString(
                R.string.full_price,
                category.amount
            )
            binding.tvCategoryPercent.text = binding.root.context.getString(
                R.string.full_profitability,
                category.percent
            )
            binding.progressCategory.progress = category.percent.toInt()
        }
    }
    companion object CategoryDiff : DiffUtil.ItemCallback<CategoryAnalytics>() {
        override fun areItemsTheSame(oldItem: CategoryAnalytics, newItem: CategoryAnalytics): Boolean {
            return oldItem.category == newItem.category
        }

        override fun areContentsTheSame(oldItem: CategoryAnalytics, newItem: CategoryAnalytics): Boolean {
            return oldItem == newItem
        }
    }
}