package com.okabe.clearcents.feature_expenses.data.mapper

import com.okabe.clearcents.feature_expenses.data.entity.CategoryEntity
import com.okabe.clearcents.feature_expenses.data.entity.CategoryExpenseEntity
import com.okabe.clearcents.feature_expenses.domain.model.CategoryExpenseModel

class CategoryExpenseMapper : Mapper<CategoryExpenseEntity, CategoryExpenseModel> {

    override fun toDomain(entity: CategoryExpenseEntity) = CategoryExpenseModel(
        categoryId = entity.categoryEntity.categoryId,
        name = entity.categoryEntity.name,
        monthlyBudget = entity.categoryEntity.monthlyBudget,
        expenses = entity.expenses
    )

    override fun toData(domain: CategoryExpenseModel) = CategoryExpenseEntity(
        categoryEntity = CategoryEntity(
            categoryId = domain.categoryId,
            name = domain.name,
            monthlyBudget = domain.monthlyBudget
        ),
        expenses = domain.expenses
    )
}