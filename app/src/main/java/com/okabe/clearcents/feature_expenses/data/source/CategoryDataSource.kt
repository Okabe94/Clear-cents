package com.okabe.clearcents.feature_expenses.data.source

import com.okabe.clearcents.feature_expenses.data.entity.CategoryEntity

interface CategoryDataSource {
    suspend fun insertCategory(categoryEntity: CategoryEntity)
    suspend fun deleteCategory(categoryEntity: CategoryEntity)
    suspend fun deleteCategory(id: Long)
    suspend fun getCategoryById(id: Long): CategoryEntity?
    suspend fun getAllCategories(): List<CategoryEntity>
}