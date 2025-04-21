package com.okabe.clearcents.feature_expenses.data.repository

import com.okabe.clearcents.feature_expenses.data.entity.ExpenseEntity
import java.util.Date

interface ExpenseRepository {
    suspend fun getExpenseById(id: Long): ExpenseEntity?
    suspend fun getExpensesBetweenDates(startDate: Date, endDate: Date): List<ExpenseEntity>
    suspend fun getExpensesForCategoryBetweenDates(
        categoryId: Long,
        startDate: Date,
        endDate: Date
    ): List<ExpenseEntity>

    suspend fun insertExpense(expenseEntity: ExpenseEntity)
    suspend fun deleteExpense(expenseEntity: ExpenseEntity)
}