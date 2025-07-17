package com.okabe.clearcents.feature_expenses.domain.repository

import com.okabe.clearcents.feature_expenses.domain.model.CategoryModel

interface CategoryRepository {
    suspend fun getAllCategories(): List<CategoryModel>
    suspend fun getCategoryById(id: Long): CategoryModel?
    suspend fun insertCategory(categoryModel: CategoryModel)
    suspend fun deleteCategory(categoryId: Long)
    suspend fun deleteCategory(categoryModel: CategoryModel)
}