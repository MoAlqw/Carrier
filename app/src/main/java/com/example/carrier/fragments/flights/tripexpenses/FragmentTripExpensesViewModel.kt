package com.example.carrier.fragments.flights.tripexpenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrier.model.ExpensesTripUi
import com.example.carrier.model.toExpensesTripUi
import com.example.domain.usecase.GetTripWithExpensesByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FragmentTripExpensesViewModel @Inject constructor(
    private val getTripWithExpensesByIdUseCase: GetTripWithExpensesByIdUseCase
) : ViewModel() {

    private val tripId = MutableStateFlow<Long?>(null)

    val trip: StateFlow<ExpensesTripUi?> =
        tripId
            .filterNotNull()
            .flatMapLatest { id ->
                getTripWithExpensesByIdUseCase(id).map {
                    it.toExpensesTripUi()
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )

    fun initTrip(id: Long) {
        tripId.value = id
    }
}