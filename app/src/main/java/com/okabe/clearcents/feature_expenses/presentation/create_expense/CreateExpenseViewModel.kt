package com.okabe.clearcents.feature_expenses.presentation.create_expense

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateExpenseViewModel : ViewModel() {

    private val _state = MutableStateFlow(CreateExpenseState())
    val state = _state.asStateFlow()

    fun onAction(action: CreateExpenseAction) {
        when (action) {
            is CreateExpenseAction.OnAmountChange ->
                _state.update { it.copy(amount = action.amount.toLong(), readyToSave = isValid()) }

            CreateExpenseAction.OnBlockDropDown ->
                _state.update { it.copy(showCategoryPicker = false) }

            is CreateExpenseAction.OnCategorySelected -> {}
            is CreateExpenseAction.OnDateChange -> {}

            CreateExpenseAction.OnDatePickerHide ->
                _state.update { it.copy(showDatePicker = false) }

            CreateExpenseAction.OnDatePickerShow ->
                _state.update { it.copy(showDatePicker = true) }

            is CreateExpenseAction.OnDescriptionChange ->
                _state.update { it.copy(description = action.description, readyToSave = isValid()) }

            CreateExpenseAction.OnDropDownToggle ->
                _state.update { it.copy(showCategoryPicker = !it.showCategoryPicker) }

            CreateExpenseAction.OnSave -> {}
        }
    }

    private fun isValid(): Boolean = _state.value.run {
        categorySelected != null && amount > 0L
    }
}