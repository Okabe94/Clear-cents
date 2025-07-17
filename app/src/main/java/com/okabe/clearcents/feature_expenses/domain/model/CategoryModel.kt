package com.okabe.clearcents.feature_expenses.domain.model

data class CategoryModel(
    val categoryId: Long = 0L,
    val name: String,
    val monthlyBudget: Long?
)