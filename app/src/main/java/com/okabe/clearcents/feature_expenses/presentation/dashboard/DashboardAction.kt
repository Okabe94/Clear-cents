package com.okabe.clearcents.feature_expenses.presentation.dashboard

sealed interface DashboardAction {
    data object OnCreateExpense : DashboardAction
    data object OnCreateCategory : DashboardAction
    data class OnCategoryDetail(val categoryId: Long) : DashboardAction
    data class OnDeleteCategory(val categoryId: Long) : DashboardAction
}