package com.okabe.clearcents.feature_expenses.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object DashboardDestination : Destination

    @Serializable
    data object CreateExpenseDestination : Destination

    @Serializable
    data class CategoryDetailDestination(val id: Long) : Destination

    @Serializable
    data object CreateCategoryDestination : Destination
}
