package com.okabe.clearcents.feature_expenses.presentation.dashboard

data class DashboardState(
    val totalExpenses: Long = 0,
    val categories: List<DashboardCategory> = emptyList(),
)

data class DashboardCategory(
    val id: Long = 0L,
    val name: String = "",
    val monthlyBudget: Long? = null,
    val spentAmount: Long = 0
)
