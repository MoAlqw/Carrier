package com.example.carrier.model

import com.example.domain.model.ExpenseCategory
import java.time.Instant

data class CreateExpanseFormState(
    var category: ExpenseCategory? = null,
    var name: String = "",
    var sum: String = "",
    var date: Instant? = null
) {
    fun isValid(): Boolean {
        return category != null &&
                name.isNotBlank() &&
                sum.toLongOrNull() != null &&
                date != null
    }
}