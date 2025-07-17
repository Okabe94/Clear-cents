package com.okabe.clearcents.feature_expenses.data.repository

import com.okabe.clearcents.feature_expenses.data.mapper.CategoryExpenseMapper
import com.okabe.clearcents.feature_expenses.data.source.CategoryExpenseDataSource
import com.okabe.clearcents.feature_expenses.domain.model.CategoryExpenseModel
import com.okabe.clearcents.feature_expenses.domain.repository.CategoryExpenseRepository
import kotlinx.coroutines.flow.map

class CategoryExpenseRepositoryImpl(
    private val dataSource: CategoryExpenseDataSource,
    private val mapper: CategoryExpenseMapper
) : CategoryExpenseRepository {

    override fun getAllCategoriesWithExpenses() =
        dataSource.getAllCategoriesWithExpenses()
            .map { entities ->
                entities.map { mapper.toDomain(it) }
            }

    override suspend fun getCategoryWithExpenses(categoryId: Long): CategoryExpenseModel? =
        dataSource.getCategoryWithExpenses(categoryId)
            ?.let { mapper.toDomain(it) }

    override suspend fun getCategorySumForMonth(categoryId: Long, yearMonth: String) =
        dataSource.getCategorySumForMonth(categoryId, yearMonth)

    override suspend fun getTotalExpensesForMonth(yearMonth: String) =
        dataSource.getTotalExpensesForMonth(yearMonth)

}