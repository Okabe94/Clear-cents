package com.okabe.clearcents.feature_expenses.data.repository

import com.okabe.clearcents.feature_expenses.data.mapper.ExpenseMapper
import com.okabe.clearcents.feature_expenses.data.source.ExpenseDataSource
import com.okabe.clearcents.feature_expenses.domain.model.ExpenseModel
import com.okabe.clearcents.feature_expenses.domain.repository.ExpenseRepository
import java.util.Date

class ExpenseRepositoryImpl(
    private val dataSource: ExpenseDataSource,
    private val mapper: ExpenseMapper
) : ExpenseRepository {

    override suspend fun getExpenseById(id: Long): ExpenseModel? =
        dataSource.getExpenseById(id)?.let { mapper.toDomain(it) }

    override suspend fun getExpensesBetweenDates(
        startDate: Date,
        endDate: Date
    ): List<ExpenseModel> = dataSource.getExpensesBetweenDates(
        startDate = startDate,
        endDate = endDate
    ).map { mapper.toDomain(it) }

    override suspend fun getExpensesForCategoryBetweenDates(
        categoryId: Long,
        startDate: Date,
        endDate: Date
    ): List<ExpenseModel> = dataSource.getExpensesForCategoryBetweenDates(
        categoryId = categoryId,
        startDate = startDate,
        endDate = endDate
    ).map { mapper.toDomain(it) }

    override suspend fun insertExpense(expenseModel: ExpenseModel) {
        dataSource.insertExpense(mapper.toData(expenseModel))
    }

    override suspend fun deleteExpense(expenseModel: ExpenseModel) {
        dataSource.deleteExpense(mapper.toData(expenseModel))
    }

}
