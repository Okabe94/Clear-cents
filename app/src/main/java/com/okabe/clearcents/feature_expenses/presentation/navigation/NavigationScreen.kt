package com.okabe.clearcents.feature_expenses.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Home : Screen

    @Serializable
    data object CreateExpense : Screen

    @Serializable
    data class CategoryDetail(val id: String) : Screen

    @Serializable
    data object CreateCategory : Screen
}
