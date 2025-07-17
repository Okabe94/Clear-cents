package com.okabe.clearcents.feature_expenses.domain.repository

import com.okabe.clearcents.feature_expenses.domain.model.ExpenseModel
import java.util.Date

interface ExpenseRepository {
    suspend fun getExpenseById(id: Long): ExpenseModel?
    suspend fun getExpensesBetweenDates(startDate: Date, endDate: Date): List<ExpenseModel>
    suspend fun getExpensesForCategoryBetweenDates(
        categoryId: Long,
        startDate: Date,
        endDate: Date
    ): List<ExpenseModel>

    suspend fun insertExpense(expenseModel: ExpenseModel)
    suspend fun deleteExpense(expenseModel: ExpenseModel)
}