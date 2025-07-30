package com.okabe.clearcents.feature_expenses.domain.model

data class CategoryExpenseModel(
    val categoryId: Long = 0L,
    val name: String,
    val monthlyBudget: Long?,
    val expenses: List<ExpenseModel>
)
