package com.okabe.clearcents.feature_expenses.data.source

import com.okabe.clearcents.feature_expenses.data.entity.CategoryExpenseEntity
import kotlinx.coroutines.flow.Flow

interface CategoryExpenseDataSource {
    fun getAllCategoriesWithExpenses(): Flow<List<CategoryExpenseEntity>>
    suspend fun getCategoryWithExpenses(categoryId: Long): CategoryExpenseEntity?

    suspend fun getCategorySumForMonth(categoryId: Long, yearMonth: String): Double?
    suspend fun getTotalExpensesForMonth(yearMonth: String): Double?
}