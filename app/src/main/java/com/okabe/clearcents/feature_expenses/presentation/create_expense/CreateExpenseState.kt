package com.okabe.clearcents.feature_expenses.presentation.create_expense

import java.util.Date

data class CreateExpenseState(
    val date: Date = Date(),
    val amount: Long = 0L,
    val showCategoryPicker: Boolean = false,
    val showDatePicker: Boolean = false,
    val categorySelected: String? = null,
    val categories: List<String> = emptyList(),
    val description: String? = null,
    val readyToSave: Boolean = false,
)