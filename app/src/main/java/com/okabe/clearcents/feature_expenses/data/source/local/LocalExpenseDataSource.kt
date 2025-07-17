package com.okabe.clearcents.feature_expenses.data.source.local

import com.okabe.clearcents.feature_expenses.data.dao.ExpenseDao
import com.okabe.clearcents.feature_expenses.data.entity.ExpenseEntity
import com.okabe.clearcents.feature_expenses.data.source.ExpenseDataSource
import java.util.Date

class LocalExpenseDataSource(
    private val expenseDao: ExpenseDao,
) : ExpenseDataSource {

    override suspend fun getExpenseById(id: Long): ExpenseEntity? {
        return expenseDao.getExpenseById(id)
    }

    override suspend fun getExpensesBetweenDates(
        startDate: Date,
        endDate: Date
    ): List<ExpenseEntity> {
        return expenseDao.getExpensesBetweenDates(startDate, endDate)
    }

    override suspend fun getExpensesForCategoryBetweenDates(
        categoryId: Long,
        startDate: Date,
        endDate: Date
    ): List<ExpenseEntity> {
        return expenseDao.getExpensesForCategoryBetweenDates(
            categoryId = categoryId,
            startDate = startDate,
            endDate = endDate
        )
    }

    override suspend fun insertExpense(expenseEntity: ExpenseEntity) {
        expenseDao.insertExpense(expenseEntity)
    }

    override suspend fun deleteExpense(expenseEntity: ExpenseEntity) {
        expenseDao.deleteExpense(expenseEntity)
    }

}