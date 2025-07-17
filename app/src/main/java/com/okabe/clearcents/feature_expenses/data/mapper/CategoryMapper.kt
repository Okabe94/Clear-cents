package com.okabe.clearcents.feature_expenses.data.mapper

import com.okabe.clearcents.feature_expenses.data.entity.CategoryEntity
import com.okabe.clearcents.feature_expenses.domain.model.CategoryModel

class CategoryMapper : Mapper<CategoryEntity, CategoryModel> {

    override fun toDomain(entity: CategoryEntity) = CategoryModel(
        categoryId = entity.categoryId,
        name = entity.name,
        monthlyBudget = entity.monthlyBudget
    )

    override fun toData(domain: CategoryModel) = CategoryEntity(
        categoryId = domain.categoryId,
        name = domain.name,
        monthlyBudget = domain.monthlyBudget
    )
}