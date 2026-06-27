package com.example.carrier.presentation.addexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrier.validation.expense.ExpenseValidationError
import com.example.carrier.validation.expense.ExpenseValidator
import com.example.domain.usecase.CreateExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseDialogViewModel @Inject constructor(
    private val createExpenseUseCase: CreateExpenseUseCase,
    private val expenseValidator: ExpenseValidator
) : ViewModel() {

    private val _state = MutableStateFlow(CreateExpenseForm())

    private val _errors = MutableStateFlow<Set<ExpenseValidationError>>(emptySet())
    val errors = _errors.asStateFlow()

    private val _expenseCreated = MutableSharedFlow<Unit>()
    val expenseCreated = _expenseCreated.asSharedFlow()

    private var tripId: Long? = null

    fun initTripId(id: Long) {
        tripId = id
    }

    fun updateForm(update: CreateExpenseForm.() -> CreateExpenseForm) {
        _state.update {
            it.update()
        }
    }

    fun createExpense() {
        viewModelScope.launch {
            val state = _state.value
            val errors = expenseValidator.validate(state)
            _errors.value = errors
            if (errors.isEmpty()) {
                createExpenseUseCase(state.toExpense(tripId!!))
                _expenseCreated.emit(Unit)
            }
        }
    }
}