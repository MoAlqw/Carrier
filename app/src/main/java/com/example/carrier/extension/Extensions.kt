package com.example.carrier.extension

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.carrier.R
import com.example.domain.model.ExpenseCategory

fun ExpenseCategory.toDisplayName(context: Context): String = when (this) {
    ExpenseCategory.FUEL -> context.getString(R.string.fuel_)
    ExpenseCategory.ROAD -> context.getString(R.string.road)
    ExpenseCategory.REPAIR -> context.getString(R.string.repair)
    ExpenseCategory.OTHER -> context.getString(R.string.other)
}

fun ExpenseCategory.toDisplayImageAndBackground(context: Context): Pair<Drawable, Drawable> {
    when (this) {
        ExpenseCategory.FUEL -> {
            return Pair(
                AppCompatResources.getDrawable(context, R.drawable.fuel)!!,
                AppCompatResources.getDrawable(context, R.drawable.bg_plate_light_green)!!
            )
        }
        ExpenseCategory.ROAD -> {
            return Pair(
                AppCompatResources.getDrawable(context, R.drawable.road)!!,
                AppCompatResources.getDrawable(context, R.drawable.bg_plate_light_blue)!!
            )
        }
        ExpenseCategory.REPAIR -> {
            return Pair(
                AppCompatResources.getDrawable(context, R.drawable.repair)!!,
                AppCompatResources.getDrawable(context, R.drawable.bg_plate_light_brown)!!
            )
        }
        ExpenseCategory.OTHER -> {
            return Pair(
                AppCompatResources.getDrawable(context, R.drawable.money)!!,
                AppCompatResources.getDrawable(context, R.drawable.bg_plate_light_violet)!!
            )
        }
    }
}
