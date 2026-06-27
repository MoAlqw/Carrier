package com.example.carrier.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carrier.R
import com.example.carrier.databinding.ItemTripBinding
import com.example.carrier.utils.DateFormatter
import com.example.domain.model.TripStatus

class TripsAdapter(
    private val onTripClick: (TripItemUi) -> Unit
) : ListAdapter<TripItemUi, TripsAdapter.TripViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val binding = ItemTripBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TripViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TripViewHolder(
        private val binding: ItemTripBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(trip: TripItemUi) {
            binding.tvRoute.text = trip.route
            binding.tvNumberDate.text = binding.root.context.getString(
                R.string.trip_number_title_with_date,
                trip.id,
                DateFormatter.format(trip.date.toEpochMilli())
            )
            binding.tvPlate.text = binding.root.context.getString(
                R.string.vehicle_plate_string,
                trip.vehicle.brand,
                trip.vehicle.model,
                trip.vehicle.plate
            )
            binding.tvAmount.text = binding.root.context.getString(
                R.string.full_price,
                trip.amount
            )
            binding.tvAmount.setTextColor(
                binding.root.context.getColor(R.color.main_green)
            )

            binding.tvNetProfit.text = binding.root.context.getString(
                R.string.full_price,
                trip.netProfit
            )
            binding.tvNetProfit.setTextColor(
                binding.root.context.getColor(
                    if (trip.netProfit >= 0) R.color.main_green else R.color.red
                )
            )

            when (trip.status) {
                TripStatus.IN_PROGRESS -> {
                    binding.tvStatusBadge.text = binding.root.context.getString(R.string.on_the_way)
                    binding.tvStatusBadge.setBackgroundResource(R.drawable.bg_plate_light_green)
                    binding.tvStatusBadge.setTextColor(
                        binding.root.context.getColor(R.color.main_green)
                    )
                    binding.tvStatusBadge.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.cargo,
                        0,
                        0,
                        0
                    )
                    binding.layoutDriverVehicle.visibility = View.VISIBLE
                    binding.divider.visibility = View.VISIBLE
                }
                TripStatus.CLOSED -> {
                    binding.tvStatusBadge.text = binding.root.context.getString(R.string.close)
                    binding.tvStatusBadge.setBackgroundResource(R.drawable.bg_plate_light_grey)
                    binding.tvStatusBadge.setTextColor(
                        binding.root.context.getColor(R.color.dark_grey)
                    )
                    binding.tvStatusBadge.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.check_arrow,
                        0,
                        0,
                        0
                    )
                    binding.layoutDriverVehicle.visibility = View.VISIBLE
                    binding.divider.visibility = View.VISIBLE
                }
            }

            binding.root.setOnClickListener { onTripClick(trip) }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TripItemUi>() {
        override fun areItemsTheSame(oldItem: TripItemUi, newItem: TripItemUi) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TripItemUi, newItem: TripItemUi) =
            oldItem == newItem
    }
}