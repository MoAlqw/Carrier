package com.example.carrier.presentation.expenses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carrier.R
import com.example.carrier.databinding.ItemExpenseBinding
import com.example.carrier.extension.toDisplayImageAndBackground
import com.example.carrier.extension.toDisplayName
import com.example.carrier.common.ExpenseItemUi

class ExpensesTripAdapter:
    ListAdapter<ExpenseItemUi, ExpensesTripAdapter.ExpenseTripViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseTripViewHolder {
        val binding = ItemExpenseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ExpenseTripViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseTripViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ExpenseTripViewHolder(
        private val binding: ItemExpenseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(expense: ExpenseItemUi) {
            binding.tvAmount.text = binding.root.context.getString(
                R.string.full_cost,
                expense.amount
            )
            binding.tvCategory.text = expense.category.toDisplayName(binding.root.context)
            binding.tvNote.text = expense.name
            binding.tvDate.text = expense.date

            val (image, background) = expense.category.toDisplayImageAndBackground(binding.root.context)
            binding.layoutIconBg.background = background
            binding.ivCategoryIcon.setImageDrawable(image)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ExpenseItemUi>() {
        override fun areItemsTheSame(oldItem: ExpenseItemUi, newItem: ExpenseItemUi) =
            oldItem.date == newItem.date

        override fun areContentsTheSame(oldItem: ExpenseItemUi, newItem: ExpenseItemUi) =
            oldItem == newItem
    }
}