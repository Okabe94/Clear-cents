package com.okabe.clearcents.feature_expenses.data.repository

import com.okabe.clearcents.feature_expenses.data.entity.CategoryExpenseEntity

interface CategoryExpenseRepository {
    suspend fun getCategoryWithExpenses(categoryId: Long): CategoryExpenseEntity?
    suspend fun getAllCategoriesWithExpenses(): List<CategoryExpenseEntity>

    suspend fun getCategorySumForMonth(categoryId: Long, yearMonth: String): Double?
    suspend fun getTotalExpensesForMonth(yearMonth: String): Double?
}