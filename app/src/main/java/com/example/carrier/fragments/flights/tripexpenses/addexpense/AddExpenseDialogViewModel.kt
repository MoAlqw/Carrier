package com.example.carrier.fragments.flights.tripexpenses.addexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrier.model.CreateExpanseFormState
import com.example.domain.model.Expense
import com.example.domain.model.ExpenseCategory
import com.example.domain.usecase.CreateExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class AddExpenseDialogViewModel @Inject constructor(
    private val createExpenseUseCase: CreateExpenseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CreateExpanseFormState())

    private val _expenseCreated = MutableSharedFlow<Unit>()
    val expenseCreated = _expenseCreated.asSharedFlow()

    private var tripId: Long? = null

    fun initTripId(id: Long) {
        tripId = id
    }

    fun onCategoryChanged(category: ExpenseCategory) {
        _state.value.category = category
    }

    fun onNameOfExpanseChanged(name: String) {
        _state.value.name = name
    }

    fun onSumChanged(sum: String) {
        _state.value.sum = sum
    }

    fun onDateChanged(date: Instant) {
        _state.value.date = date
    }

    fun createExpense() {
        val state = _state.value

        if (!state.isValid()) return

        viewModelScope.launch {
            createExpenseUseCase(
                Expense(
                    id = 0,
                    category = state.category!!,
                    name = state.name,
                    amount = state.sum.toLong(),
                    tripId = tripId!!,
                    date = state.date!!
                )
            )
            _expenseCreated.emit(Unit)
        }
    }
}