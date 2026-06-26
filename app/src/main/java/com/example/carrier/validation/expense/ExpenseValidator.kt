package com.example.carrier.validation.expense

import com.example.carrier.model.CreateExpanseForm
import javax.inject.Inject

class ExpenseValidator @Inject constructor() {

    fun validate(form: CreateExpanseForm): Set<ExpenseValidationError> {
        val errors = mutableSetOf<ExpenseValidationError>()

        if (form.name.isBlank()) {
            errors.add(ExpenseValidationError.NameEmpty)
        }

        if (form.category == null) {
            errors.add(ExpenseValidationError.CategoryEmpty)
        }

        val sum = form.sum.toLongOrNull()
        when {
            form.sum.isBlank() ->
                errors += ExpenseValidationError.SumEmpty

            sum == null || sum <= 0 ->
                errors += ExpenseValidationError.SumInvalid
        }

        if (form.date == null) {
            errors.add(ExpenseValidationError.DateEmpty)
        }

        return errors
    }
}