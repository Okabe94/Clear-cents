package com.okabe.clearcents.feature_expenses.domain.repository

import com.okabe.clearcents.feature_expenses.domain.model.CategoryExpenseModel
import kotlinx.coroutines.flow.Flow

interface CategoryExpenseRepository {
    fun getAllCategoriesWithExpenses(): Flow<List<CategoryExpenseModel>>
    suspend fun getCategoryWithExpenses(categoryId: Long): CategoryExpenseModel?

    suspend fun getCategorySumForMonth(categoryId: Long, yearMonth: String): Double?
    suspend fun getTotalExpensesForMonth(yearMonth: String): Double?
}