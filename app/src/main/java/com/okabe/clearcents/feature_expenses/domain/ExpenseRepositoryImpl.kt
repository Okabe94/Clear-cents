package com.okabe.clearcents.feature_expenses.domain

import com.okabe.clearcents.feature_expenses.data.dao.ExpenseDao
import com.okabe.clearcents.feature_expenses.data.entity.ExpenseEntity
import com.okabe.clearcents.feature_expenses.data.repository.ExpenseRepository
import java.util.Date

class ExpenseRepositoryImpl(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {

    override suspend fun getExpenseById(id: Long) = expenseDao.getExpenseById(id)
    override suspend fun getExpensesBetweenDates(startDate: Date, endDate: Date) =
        expenseDao.getExpensesBetweenDates(startDate, endDate)

    override suspend fun getExpensesForCategoryBetweenDates(
        categoryId: Long,
        startDate: Date,
        endDate: Date
    ): List<ExpenseEntity> =
        expenseDao.getExpensesForCategoryBetweenDates(categoryId, startDate, endDate)

    override suspend fun insertExpense(expenseEntity: ExpenseEntity) =
        expenseDao.insertExpense(expenseEntity)

    override suspend fun deleteExpense(expenseEntity: ExpenseEntity) =
        expenseDao.deleteExpense(expenseEntity)
}
