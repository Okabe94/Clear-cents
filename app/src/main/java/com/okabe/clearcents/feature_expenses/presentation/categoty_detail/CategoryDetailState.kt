package com.okabe.clearcents.feature_expenses.presentation.categoty_detail

import com.okabe.clearcents.feature_expenses.domain.model.ExpenseModel

data class CategoryDetailState(
    val name: String = "",
    val monthlyBudget: Long = 0L,
    val totalSpent: Long = 0L,
    val showCategoryMenu: Boolean = false,
    val showCategoryDialog: Boolean = false,
    val showDeleteCategoryDialog: Boolean = false,
    val expenseToDelete: ExpenseModel? = null,
    val expenses: List<ExpenseModel> = emptyList()
)