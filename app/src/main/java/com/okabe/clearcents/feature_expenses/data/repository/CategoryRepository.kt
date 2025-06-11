package com.okabe.clearcents.feature_expenses.data.repository

import com.okabe.clearcents.feature_expenses.data.entity.CategoryEntity

interface CategoryRepository {
    suspend fun getAllCategories(): List<CategoryEntity>
    suspend fun getCategoryById(id: Long): CategoryEntity?
    suspend fun insertCategory(categoryEntity: CategoryEntity)
    suspend fun deleteCategory(categoryId: Long)
    suspend fun deleteCategory(categoryEntity: CategoryEntity)
}