package com.okabe.clearcents.feature_expenses.domain.model

import com.okabe.clearcents.feature_expenses.data.entity.ExpenseEntity

data class CategoryExpenseModel(
    val categoryId: Long = 0L,
    val name: String,
    val monthlyBudget: Long?,
    val expenses: List<ExpenseEntity>
)
