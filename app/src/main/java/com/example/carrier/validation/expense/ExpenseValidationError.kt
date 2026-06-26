package com.example.carrier.validation.expense

import com.example.carrier.validation.ValidationError

interface ExpenseValidationError: ValidationError {
    object CategoryEmpty: ExpenseValidationError
    object NameEmpty: ExpenseValidationError
    object SumEmpty: ExpenseValidationError
    object SumInvalid: ExpenseValidationError
    object DateEmpty: ExpenseValidationError
}