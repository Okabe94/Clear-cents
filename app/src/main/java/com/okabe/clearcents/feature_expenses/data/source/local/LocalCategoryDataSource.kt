package com.okabe.clearcents.feature_expenses.data.source.local

import com.okabe.clearcents.feature_expenses.data.dao.CategoryDao
import com.okabe.clearcents.feature_expenses.data.entity.CategoryEntity
import com.okabe.clearcents.feature_expenses.data.source.CategoryDataSource

class LocalCategoryDataSource(
    private val categoryDao: CategoryDao
) : CategoryDataSource {

    override suspend fun insertCategory(categoryEntity: CategoryEntity) {
        categoryDao.insertCategory(categoryEntity)
    }

    override suspend fun deleteCategory(categoryEntity: CategoryEntity) {
        categoryDao.deleteCategory(categoryEntity)
    }

    override suspend fun deleteCategory(id: Long) {
        categoryDao.deleteCategory(id)
    }

    override suspend fun getCategoryById(id: Long): CategoryEntity? {
        return categoryDao.getCategoryById(id)
    }

    override suspend fun getAllCategories(): List<CategoryEntity> {
        return categoryDao.getAllCategories()
    }
}