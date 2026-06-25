package com.example.carrier.extension

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import com.example.carrier.R
import com.example.carrier.validation.ValidationError
import com.example.domain.model.ExpenseCategory
import com.google.android.material.textfield.TextInputLayout

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

fun TextInputLayout.showError(
    errors: Set<ValidationError>,
    emptyError: ValidationError,
    @StringRes emptyMessage: Int,
    invalidError: ValidationError? = null,
    @StringRes invalidMessage: Int? = null,
) {
    error = when {
        emptyError in errors ->
            context.getString(emptyMessage)

        invalidError != null &&
                invalidMessage != null &&
                invalidError in errors ->
            context.getString(invalidMessage)

        else -> null
    }
}