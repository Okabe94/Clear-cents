package com.okabe.clearcents.feature_expenses.domain.model

import java.util.Date

data class ExpenseModel(
    val expenseId: Long = 0L,
    val categoryIdForeign: Long,
    val amount: Long,
    val date: Date,
    val description: String? = null
)
