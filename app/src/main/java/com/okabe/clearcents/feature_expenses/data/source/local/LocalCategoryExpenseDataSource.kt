package com.okabe.clearcents.feature_expenses.data.source.local

import com.okabe.clearcents.feature_expenses.data.dao.CategoryExpenseDao
import com.okabe.clearcents.feature_expenses.data.entity.CategoryExpenseEntity
import com.okabe.clearcents.feature_expenses.data.source.CategoryExpenseDataSource
import kotlinx.coroutines.flow.Flow

class LocalCategoryExpenseDataSource(
    private val categoryExpenseDao: CategoryExpenseDao
) : CategoryExpenseDataSource {

    override fun getAllCategoriesWithExpenses(): Flow<List<CategoryExpenseEntity>> {
        return categoryExpenseDao.getAllCategoriesWithExpenses()
    }

    override suspend fun getCategoryWithExpenses(categoryId: Long): CategoryExpenseEntity? {
        return categoryExpenseDao.getCategoryWithExpenses(categoryId)
    }

    override suspend fun getCategorySumForMonth(categoryId: Long, yearMonth: String): Double? {
        return categoryExpenseDao.getCategorySumForMonth(categoryId, yearMonth)
    }

    override suspend fun getTotalExpensesForMonth(yearMonth: String): Double? {
        return categoryExpenseDao.getTotalExpensesForMonth(yearMonth)
    }
}