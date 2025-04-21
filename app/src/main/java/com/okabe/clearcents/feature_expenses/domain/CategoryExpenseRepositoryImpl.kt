package com.okabe.clearcents.feature_expenses.domain

import com.okabe.clearcents.feature_expenses.data.dao.CategoryExpenseDao
import com.okabe.clearcents.feature_expenses.data.repository.CategoryExpenseRepository

class CategoryExpenseRepositoryImpl(
    private val categoryExpenseDao: CategoryExpenseDao
) : CategoryExpenseRepository {
    override suspend fun getCategoryWithExpenses(categoryId: Long) =
        categoryExpenseDao.getCategoryWithExpenses(categoryId)

    override suspend fun getAllCategoriesWithExpenses() =
        categoryExpenseDao.getAllCategoriesWithExpenses()

    override suspend fun getCategorySumForMonth(categoryId: Long, yearMonth: String): Double? =
        categoryExpenseDao.getCategorySumForMonth(categoryId, yearMonth)

    override suspend fun getTotalExpensesForMonth(yearMonth: String): Double? =
        categoryExpenseDao.getTotalExpensesForMonth(yearMonth)
}