package com.okabe.clearcents.feature_expenses.presentation.create_category

sealed interface CreateCategoryAction {
    data object OnGoBack : CreateCategoryAction
    data object OnSave : CreateCategoryAction
    data class OnNameChange(val name: String) : CreateCategoryAction
    data class OnBudgetChange(val budget: String) : CreateCategoryAction
}