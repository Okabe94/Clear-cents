package com.okabe.clearcents.feature_expenses.presentation.create_expense

sealed interface CreateExpenseAction {
    data class OnDateChange(val date: String) : CreateExpenseAction
    data class OnCategorySelected(val category: String) : CreateExpenseAction
    data class OnAmountChange(val amount: String) : CreateExpenseAction
    data class OnDescriptionChange(val description: String?) : CreateExpenseAction
    data object OnDropDownToggle : CreateExpenseAction
    data object OnDatePickerShow : CreateExpenseAction
    data object OnDatePickerHide : CreateExpenseAction
    data object OnBlockDropDown : CreateExpenseAction
    data object OnSave : CreateExpenseAction
}