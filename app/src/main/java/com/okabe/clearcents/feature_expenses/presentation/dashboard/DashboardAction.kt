package com.okabe.clearcents.feature_expenses.presentation.dashboard

sealed interface DashboardAction {
    data object OnAddExpense : DashboardAction
    data object OnAddCategory : DashboardAction
    data class OnCategoryDetail(val categoryId: String) : DashboardAction
    data class OnDeleteCategory(val categoryId: String) : DashboardAction
}