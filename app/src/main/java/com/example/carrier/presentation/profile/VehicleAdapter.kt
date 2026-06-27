package com.example.carrier.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carrier.R
import com.example.carrier.databinding.ItemVehicleBinding
import com.example.carrier.common.VehicleItemUi
import kotlin.math.roundToLong

class VehicleAdapter :
    ListAdapter<VehicleItemUi, VehicleAdapter.VehicleViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVehicleBinding.inflate(inflater, parent, false)
        return VehicleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class VehicleViewHolder(
        private val binding: ItemVehicleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VehicleItemUi) {
            binding.tvBrandWithModel.text =
                binding.root.context.getString(
                    R.string.vehicle_brand_and_model,
                    item.brand,
                    item.model
                )
            binding.tvPlateWithFuel.text =
                binding.root.context.getString(
                    R.string.fuel_100,
                    item.plate,
                    item.fuelConsumption.roundToLong()
                )
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<VehicleItemUi>() {
            override fun areItemsTheSame(oldItem: VehicleItemUi, newItem: VehicleItemUi): Boolean {
                return oldItem.plate == newItem.plate
            }
            override fun areContentsTheSame(oldItem: VehicleItemUi, newItem: VehicleItemUi): Boolean {
                return oldItem == newItem
            }
        }
    }
}